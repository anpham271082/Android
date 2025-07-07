package com.example.my_app_android.example_mvvm1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExampleMVVM1MainViewModel : ViewModel() {
    private val _quote = MutableStateFlow("Loading...")
    val quote: StateFlow<String> = _quote

    private val apiService = ExampleMVVM1ApiService.create()

    fun fetchQuote() {
        viewModelScope.launch {
            try {
                val response = apiService.getRandomQuote()
                if (response.isSuccessful) {
                    val randomQuote = response.body()?.firstOrNull()
                    _quote.value = randomQuote?.let { "\"${it.q}\" - ${it.a ?: "Unknown"} \"${it.h}\"" }
                        ?: "No quote available"
                } else {
                    _quote.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _quote.value = "Error: ${e.message}"
            }
        }
    }
}