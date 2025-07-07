package com.example.my_app_android.example_hilt_mvvm.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_android.example_hilt_mvvm.NavigationKeys
import com.example.my_app_android.example_hilt_mvvm.data.remote.HiltMVVMFoodRemote
import com.example.my_app_android.example_hilt_mvvm.view.HiltMVVMFoodCategoryDetailsContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiltMVVMFoodCategoryDetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val foodRemote: HiltMVVMFoodRemote
) : ViewModel() {

    var state by mutableStateOf(
        HiltMVVMFoodCategoryDetailsContract.State(
            null, listOf(
            )
        )
    )
        private set

    init {
        viewModelScope.launch {
            val categoryId = stateHandle.get<String>(NavigationKeys.Arg.FOOD_CATEGORY_ID)
                ?: throw IllegalStateException("No categoryId was passed to destination.")
            val categories = foodRemote.getFoodCategories()
            val category = categories.first { it.id == categoryId }
            state = state.copy(category = category)
            val foodItems = foodRemote.getMealsByCategory(categoryId)
            state = state.copy(categoryFoodItems = foodItems)
        }
    }

}