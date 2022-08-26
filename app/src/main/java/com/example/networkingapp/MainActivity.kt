package com.example.networkingapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.networkingapp.databinding.ActivityMainBinding
import com.example.networkingapp.userAPI.model.Result
import com.squareup.picasso.Picasso

const val TAG = "MyApp"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersListAdapter: UsersListAdapter

    private val viewModel:MainViewModel by viewModels()

    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUsersList()

        alertDialog = AlertDialog.Builder(this)
            .setTitle("An error occurred")
            .setPositiveButton("Retry") { _, _ ->
                loadUsers()
            }
            .create()

        binding.requestButton.setOnClickListener {
            loadUsers()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.userAPIResponseResult.value?.getOrNull()?.results?.let {
            usersListAdapter.users = it
            displayUserDetails(it[0])
        }
    }

    private fun loadUsers() {
        viewModel.fetchDataFromRepo()

        //Load all users
        binding.progressBar.isVisible = true
        viewModel.userAPIResponseResult.observe(this) { result ->
            binding.progressBar.isVisible = false
            if (result.isSuccess && result != null) {
                with(result.getOrNull()!!) {
                    if (alertDialog.isShowing)
                        alertDialog.dismiss()
                    //display names list
                    usersListAdapter.users = results

                    //Display details for the first user
                    displayUserDetails(results[0])
                }
            } else {
                Log.e(TAG, "observer: ${result.exceptionOrNull()}")
                alertDialog
                    .setMessage(
                        if (checkConnectivity())
                            result.exceptionOrNull()?.message
                        else
                            getString(R.string.connection_lost_error_message)
                    )

                alertDialog.show()
            }
        }
    }

    private fun checkConnectivity(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    private fun displayUserDetails(user: Result) {
        with(binding) {
            tvUserName.text = user.name.first
            tvLastName.text = user.name.last
            tvGender.text = user.gender
            tvPhone.text = user.phone
            tvEmail.text = user.email

            Picasso.with(this@MainActivity)
                .load(user.picture.large)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.bad_review)
                .into(userIconImageView)
        }
    }

    private fun setupUsersList() = binding.usersRV.apply {
        usersListAdapter = UsersListAdapter()
        adapter = usersListAdapter
    }


}