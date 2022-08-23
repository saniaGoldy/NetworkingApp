package com.example.networkingapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.networkingapp.repository.MainRepository
import com.example.networkingapp.userAPI.model.RandomUserAPIResponse

class MainViewModel : ViewModel(), MainRepository.ProcessRequestResult {

    private val repository get() =  MainRepository(this)

    private val _userAPIResponseResult = MutableLiveData<Result<RandomUserAPIResponse>>()

    val userAPIResponseResult: MutableLiveData<Result<RandomUserAPIResponse>>
        get() = _userAPIResponseResult

    fun fetchDataFromRepo(){
        repository.makeUsersAPIQuery()
    }

    override fun process(
        result: Result<RandomUserAPIResponse>
    ) {
        _userAPIResponseResult.value = result
    }
}