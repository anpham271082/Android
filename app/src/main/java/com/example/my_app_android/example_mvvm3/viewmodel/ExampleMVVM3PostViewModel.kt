package com.example.my_app_android.example_mvvm3.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_android.example_mvvm3.data.api.model.ExampleMVVM3PostData
import com.example.my_app_android.example_mvvm3.data.repository.ExampleMVVM3RetrofitInstance
import kotlinx.coroutines.launch

class ExampleMVVM3PostViewModel() : ViewModel() {
    private val _posts = mutableStateOf<List<ExampleMVVM3PostData>>(emptyList())
    val posts: State<List<ExampleMVVM3PostData>> = _posts
    init{
        fetchPosts()
    }
    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                _posts.value = ExampleMVVM3RetrofitInstance.api.getPosts()
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}