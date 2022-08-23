package com.example.networkingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.networkingapp.databinding.ActivityMainBinding

const val TAG = "MyApp"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersListAdapter: UsersListAdapter

    private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUsersList()

        binding.requestButton.setOnClickListener {
            loadUsers()
        }

    }

    private fun loadUsers() {
        viewModel.fetchDataFromRepo()

        binding.progressBar.isVisible = true
        viewModel.userAPIResponseResult.observe(this) { result ->
            if (result.isSuccess && result != null) {
                usersListAdapter.users = result.getOrNull()!!.results
                binding.progressBar.isVisible = false
            } else {
                Log.e(TAG, "observer: ${result.exceptionOrNull()?.printStackTrace()}")
            }
        }
    }

    private fun setupUsersList() = binding.usersRV.apply {
        usersListAdapter = UsersListAdapter()
        adapter = usersListAdapter
    }


}