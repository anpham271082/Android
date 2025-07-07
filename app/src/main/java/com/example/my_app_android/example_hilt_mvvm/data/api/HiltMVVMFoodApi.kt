package com.example.my_app_android.example_hilt_mvvm.data.api

import com.example.my_app_android.example_hilt_mvvm.data.model.HiltMVVMFoodCategories
import com.example.my_app_android.example_hilt_mvvm.data.model.HiltMVVMMeals
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HiltMVVMFoodApi @Inject constructor(private val service: Service) {

    suspend fun getFoodCategories(): HiltMVVMFoodCategories = service.getFoodCategories()
    suspend fun getMealsByCategory(categoryId: String): HiltMVVMMeals =
        service.getMealsByCategory(categoryId)

    interface Service {
        @GET("categories.php")
        suspend fun getFoodCategories(): HiltMVVMFoodCategories

        @GET("filter.php")
        suspend fun getMealsByCategory(@Query("c") categoryId: String): HiltMVVMMeals
    }

    companion object {
        const val API_URL = "https://www.themealdb.com/api/json/v1/1/"
    }
}