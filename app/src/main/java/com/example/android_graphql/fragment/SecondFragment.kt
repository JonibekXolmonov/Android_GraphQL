package com.example.android_graphql.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.android_graphql.*
import com.example.android_graphql.databinding.FragmentSecondBinding
import com.example.android_graphql.networking.GraphQL
import kotlinx.coroutines.launch

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var _binding: FragmentSecondBinding
    private lateinit var userId: String
    private lateinit var user: UsersListQuery.User

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
                GraphQL.get().query(UserByIdQuery(userId)).execute()
            } catch (e: ApolloException) {
                Log.d("TAG", "insertUser: ${e.localizedMessage}")
            }

            Log.d("TAG", "getUserById: $response")
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

//                edtName.setText(user.name)
//                edtRocket.setText(user.rocket)
//                edtTwitter.setText(user.twitter)
            }

            btnSave.setOnClickListener {
                insertUser(
                    edtName.text.toString(),
                    edtRocket.text.toString(),
                    edtTwitter.text.toString()
                )
            }

            btnDelete.setOnClickListener {
                deleteUser(userId)
            }
        }
    }

    private fun insertUser(name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
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

    private fun updateUser(id: String, name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
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
                GraphQL.get().mutation(DeleteUserMutation(id)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }
}