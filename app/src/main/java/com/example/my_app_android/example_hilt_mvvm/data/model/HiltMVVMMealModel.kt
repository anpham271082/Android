package com.example.my_app_android.example_hilt_mvvm.data.model

import com.google.gson.annotations.SerializedName

data class HiltMVVMMeals(val meals: List<HiltMVVMMealModel>)

data class HiltMVVMMealModel(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val thumbnailUrl: String,
)
