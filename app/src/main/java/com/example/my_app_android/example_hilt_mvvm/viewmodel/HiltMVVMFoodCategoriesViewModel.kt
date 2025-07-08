package com.example.my_app_android.example_hilt_mvvm.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_android.example_hilt_mvvm.data.local.HiltMVVMDataStore
import com.example.my_app_android.example_hilt_mvvm.data.remote.HiltMVVMFoodRemote
import com.example.my_app_android.example_hilt_mvvm.ui.HiltMVVMFoodCategoriesContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiltMVVMFoodCategoriesViewModel @Inject constructor(
    private val preferences: HiltMVVMDataStore,
    private val remoteSource: HiltMVVMFoodRemote) :
    ViewModel() {

    var state by mutableStateOf(
        HiltMVVMFoodCategoriesContract.State(
            categories = listOf(),
            isLoading = true
        )
    )
        private set

    var effects = Channel<HiltMVVMFoodCategoriesContract.Effect>(Channel.Factory.UNLIMITED)
        private set

    init {
        viewModelScope.launch { getFoodCategories() }
    }

    private suspend fun getFoodCategories() {
        val categories = remoteSource.getFoodCategories()
        viewModelScope.launch {
            state = state.copy(categories = categories, isLoading = false)
            effects.send(HiltMVVMFoodCategoriesContract.Effect.DataWasLoaded)
        }
    }

    val isDarkMode = preferences.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    fun toggleDarkMode() {
        Log.d("toggleDarkMode", isDarkMode.value.toString() )
        viewModelScope.launch {
            preferences.saveDarkMode(!isDarkMode.value)
        }
    }
}