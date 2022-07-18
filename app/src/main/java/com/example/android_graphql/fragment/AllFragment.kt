package com.example.android_graphql.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.exception.ApolloException
import com.example.android_graphql.R
import com.example.android_graphql.UsersListQuery
import com.example.android_graphql.adapter.TestAdapter
import com.example.android_graphql.databinding.FragmentAllBinding
import com.example.android_graphql.networking.GraphQL
import kotlinx.coroutines.launch

class AllFragment : Fragment(R.layout.fragment_all) {

    private lateinit var _binding: FragmentAllBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAllBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        getUserList()

        _binding.btnAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_allFragment_to_secondFragment,
                bundleOf("user" to "")
            )
        }
    }

    private fun getUserList() {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().query(UsersListQuery(10)).execute()
            } catch (e: ApolloException) {
                Log.d("TAG", "getUserList: ${e.localizedMessage}")
                return@launchWhenResumed
            }
            _binding.rvItem.adapter = TestAdapter(response.data?.users) {
                findNavController().navigate(
                    R.id.action_allFragment_to_secondFragment,
                    bundleOf("user" to it.toString())
                )
            }
        }
    }
}