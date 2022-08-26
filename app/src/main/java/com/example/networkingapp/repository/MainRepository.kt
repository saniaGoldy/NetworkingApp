package com.example.networkingapp.repository

import com.example.networkingapp.userAPI.RetrofitInstance
import com.example.networkingapp.userAPI.model.RandomUserAPIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainRepository(private val resultProcessor: ProcessRequestResult) {

    fun makeUsersAPIQuery(){

        RetrofitInstance.api.getRandomUserAPIResponse(2, 3).enqueue(object :
            Callback<RandomUserAPIResponse> {
            override fun onResponse(
                call: Call<RandomUserAPIResponse>,
                response: Response<RandomUserAPIResponse>
            ) {
                resultProcessor.process(
                    if (response.isSuccessful && response.body() != null) {
                        Result.success(response.body()!!)
                    } else {
                        Result.failure(IOException("Bad Response"))
                    }
                )
            }

            override fun onFailure(call: Call<RandomUserAPIResponse>, t: Throwable) {
                resultProcessor.process(Result.failure(t))
            }

        })
    }

    interface ProcessRequestResult {
        fun process(result: Result<RandomUserAPIResponse>)
    }
}