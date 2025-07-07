package com.example.my_app_android.example_hilt_mvvm.data.model

import com.google.gson.annotations.SerializedName
data class HiltMVVMFoodCategories(val categories: List<HiltMVVMFoodCategoryModel>)

data class HiltMVVMFoodCategoryModel(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String,
    @SerializedName("strCategoryThumb") val thumbnailUrl: String,
    @SerializedName("strCategoryDescription") val description: String = ""
)