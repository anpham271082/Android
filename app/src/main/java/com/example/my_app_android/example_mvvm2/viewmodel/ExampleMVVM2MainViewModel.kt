package com.example.my_app_android.example_mvvm2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_android.example_mvvm2.model.ExampleMVVM2UserData
import com.example.my_app_android.example_mvvm2.model.ExampleMVVM2UserRepository
import kotlinx.coroutines.launch

class ExampleMVVM2MainViewModel: ViewModel() {
    val userRepository:ExampleMVVM2UserRepository = ExampleMVVM2UserRepository()
    private val _userData = MutableLiveData<ExampleMVVM2UserData>()
    val userData : LiveData<ExampleMVVM2UserData> = _userData

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading : LiveData<Boolean> = _isLoading
    fun  getUserData(){
        _isLoading.postValue(true)
        viewModelScope.launch {
            val userResult = userRepository.fetchUserData()
            _userData.postValue(userResult)
            _isLoading.postValue(false)
        }
    }
}