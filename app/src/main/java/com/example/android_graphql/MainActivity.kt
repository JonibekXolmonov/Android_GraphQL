package com.example.android_graphql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.android_graphql.networking.GraphQL
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        updateUser("#dc5b9a6a-34f5-4d0d-ad64-f9a6e21da66a", "ABCDEF", "SGGGSGS", "NOOOOO")
//        deleteUser("#dc5b9a6a-34f5-4d0d-ad64-f9a6e21da66a")
//        insertUser("New name", "New rocket", "New twitter")
    }
}