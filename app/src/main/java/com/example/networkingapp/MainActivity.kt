package com.example.networkingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.R
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.networkingapp.databinding.ActivityMainBinding
import com.example.networkingapp.userAPI.RetrofitInstance
import com.example.networkingapp.userAPI.model.RandomUserAPIResponse
import retrofit2.Call
import retrofit2.Callback

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

const val TAG = "MyApp"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersListAdapter: UsersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUsersList()

        binding.progressBar.isVisible = true
        RetrofitInstance.api.getRandomUserAPIResponse(2,3).enqueue(object : Callback<RandomUserAPIResponse>{
            override fun onResponse(
                call: Call<RandomUserAPIResponse>,
                response: Response<RandomUserAPIResponse>
            ) {
                binding.progressBar.isVisible = false
                if (response.isSuccessful && response.body() != null)
                    usersListAdapter.users = response.body()!!.results
            }

            override fun onFailure(call: Call<RandomUserAPIResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })

        /*lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true

            val response = try {
                RetrofitInstance.api.getRandomUserAPIResponse(2,3).execute()
            } catch (e: IOException){
                Log.e(TAG, "IOException: check your internet connection" )
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }catch (e: HttpException){
                Log.e(TAG, "HttpException: unexpected response" )
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }


            if (response.isSuccessful && response.body() != null){
                usersListAdapter.users = response.body()!!.results
            }else{
                Log.e(TAG, "Response not successful" )
            }

            binding.progressBar.isVisible = false
        }*/
    }

    private fun setupUsersList() = binding.usersRV.apply {
        usersListAdapter = UsersListAdapter()
        adapter = usersListAdapter
    }


}