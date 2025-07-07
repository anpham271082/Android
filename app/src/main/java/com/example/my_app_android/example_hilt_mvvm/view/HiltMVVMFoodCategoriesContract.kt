package com.example.my_app_android.example_hilt_mvvm.view

import com.example.my_app_android.example_hilt_mvvm.data.model.HiltMVVMFoodItemModel

class HiltMVVMFoodCategoriesContract {

    data class State(
        val categories: List<HiltMVVMFoodItemModel> = listOf(),
        val isLoading: Boolean = false
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}