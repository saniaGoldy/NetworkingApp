package com.example.networkingapp.userAPI

import com.example.networkingapp.userAPI.model.RandomUserAPIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAPI {

    @GET("/api")
    fun getRandomUserAPIResponse(
        @Query("page") page: Int,
        @Query("results") results: Int
    ): Call<RandomUserAPIResponse>
}