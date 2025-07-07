package com.example.my_app_android.example_hilt_mvvm.data.model

data class HiltMVVMFoodItemModel(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val description: String = ""
)
