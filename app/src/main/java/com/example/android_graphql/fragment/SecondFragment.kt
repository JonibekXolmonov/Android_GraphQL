package com.example.android_graphql.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.exception.ApolloException
import com.example.android_graphql.*
import com.example.android_graphql.databinding.FragmentSecondBinding
import com.example.android_graphql.networking.GraphQL
import kotlinx.coroutines.launch

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var _binding: FragmentSecondBinding
    private lateinit var userId: String
    private lateinit var user: UserByIdQuery.Users_by_pk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            userId = arguments?.getString("user", "")!!
            Log.d("TAG", "onCreate: $userId")
        }

        if (userId.isNotEmpty()) {
            getUserById(userId)
        }
    }

    private fun getUserById(userId: String) {
        lifecycleScope.launch {
            val response = try {
                GraphQL.get().query(UserByIdQuery(userId)).execute().data?.users_by_pk
            } catch (e: ApolloException) {
                Log.d("TAG", "insertUser: ${e.localizedMessage}")
            }

            Log.d("TAG", "getUserById: $response")
            user = response as UserByIdQuery.Users_by_pk
            _binding.apply {
                edtName.setText(user.name)
                edtRocket.setText(user.rocket)
                edtTwitter.setText(user.twitter)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSecondBinding.bind(view)

        initViews()
    }

    private fun initViews() {

        _binding.apply {
            if (userId.isNotEmpty()) {
                btnDelete.visibility = View.VISIBLE
                btnSave.text = "Update"
            }

            btnSave.setOnClickListener {
                if (userId.isNotEmpty())
                    updateUser(
                        userId,
                        edtName.text.toString(),
                        edtRocket.text.toString(),
                        edtTwitter.text.toString()
                    )
                else {
                    insertUser(
                        edtName.text.toString(),
                        edtRocket.text.toString(),
                        edtTwitter.text.toString()
                    )
                }
            }

            btnDelete.setOnClickListener {
                deleteUser(userId)
            }
        }
    }


    private fun insertUser(name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                back("Saved bro!!")
                GraphQL.get().mutation(
                    InsertUserMutation(name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }

    private fun back(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun updateUser(id: String, name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                back("Updated bro!!!")
                GraphQL.get().mutation(
                    UpdateUserMutation(id, name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }

    private fun deleteUser(id: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                back("Deleted bro!!!")
                GraphQL.get().mutation(DeleteUserMutation(id)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }
}