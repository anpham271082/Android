package com.example.my_app_android.example_hilt_mvvm.data.remote

import com.example.my_app_android.example_hilt_mvvm.data.model.HiltMVVMFoodCategories
import com.example.my_app_android.example_hilt_mvvm.data.model.HiltMVVMFoodItemModel
import com.example.my_app_android.example_hilt_mvvm.data.model.HiltMVVMMeals
import com.example.my_app_android.example_hilt_mvvm.data.api.HiltMVVMFoodApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HiltMVVMFoodRemote @Inject constructor(private val foodApi: HiltMVVMFoodApi) {

    private var cachedCategories: List<HiltMVVMFoodItemModel>? = null

    suspend fun getFoodCategories(): List<HiltMVVMFoodItemModel> = withContext(Dispatchers.IO) {
        var cachedCategories = cachedCategories
        if (cachedCategories == null) {
            cachedCategories = foodApi.getFoodCategories().mapCategoriesToItems()
            this@HiltMVVMFoodRemote.cachedCategories = cachedCategories
        }
        return@withContext cachedCategories
    }

    suspend fun getMealsByCategory(categoryId: String) = withContext(Dispatchers.IO) {
        val categoryName = getFoodCategories().first { it.id == categoryId }.name
        return@withContext foodApi.getMealsByCategory(categoryName).mapMealsToItems()
    }

    private fun HiltMVVMFoodCategories.mapCategoriesToItems(): List<HiltMVVMFoodItemModel> {
        return this.categories.map { category ->
            HiltMVVMFoodItemModel(
                id = category.id,
                name = category.name,
                description = category.description,
                thumbnailUrl = category.thumbnailUrl
            )
        }
    }

    private fun HiltMVVMMeals.mapMealsToItems(): List<HiltMVVMFoodItemModel> {
        return this.meals.map { category ->
            HiltMVVMFoodItemModel(
                id = category.id,
                name = category.name,
                thumbnailUrl = category.thumbnailUrl
            )
        }
    }

}