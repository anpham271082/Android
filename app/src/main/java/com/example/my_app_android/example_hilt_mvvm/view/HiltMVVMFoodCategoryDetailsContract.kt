package com.example.my_app_android.example_hilt_mvvm.view

import com.example.my_app_android.example_hilt_mvvm.data.model.HiltMVVMFoodItemModel

class HiltMVVMFoodCategoryDetailsContract {
    data class State(
        val category: HiltMVVMFoodItemModel?,
        val categoryFoodItems: List<HiltMVVMFoodItemModel>
    )
}