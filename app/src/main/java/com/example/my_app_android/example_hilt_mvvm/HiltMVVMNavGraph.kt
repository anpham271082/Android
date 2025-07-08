package com.example.my_app_android.example_hilt_mvvm

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.my_app_android.example_hilt_mvvm.NavigationKeys.Arg.FOOD_CATEGORY_ID
import com.example.my_app_android.example_hilt_mvvm.ui.HiltMVVMFoodCategoriesScreen
import com.example.my_app_android.example_hilt_mvvm.ui.HiltMVVMFoodCategoryDetailsScreen
import com.example.my_app_android.example_hilt_mvvm.viewmodel.HiltMVVMFoodCategoriesViewModel
import com.example.my_app_android.example_hilt_mvvm.viewmodel.HiltMVVMFoodCategoryDetailsViewModel
import kotlinx.coroutines.flow.receiveAsFlow

object NavigationKeys {
    object Arg {
        const val FOOD_CATEGORY_ID = "foodCategoryName"
    }
    object Route {
        const val FOOD_CATEGORIES_LIST = "food_categories_list"
        const val FOOD_CATEGORY_DETAILS = "$FOOD_CATEGORIES_LIST/{$FOOD_CATEGORY_ID}"
    }
}
@Composable
fun HiltMVVMNavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationKeys.Route.FOOD_CATEGORIES_LIST) {
        composable(route = NavigationKeys.Route.FOOD_CATEGORIES_LIST) {
            FoodCategoriesDestination(navController)
        }
        composable(
            route = NavigationKeys.Route.FOOD_CATEGORY_DETAILS,
            arguments = listOf(navArgument(FOOD_CATEGORY_ID) {
                type = NavType.StringType
            })
        ) {
            FoodCategoryDetailsDestination()
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun FoodCategoriesDestination(navController: NavHostController) {
    val viewModel: HiltMVVMFoodCategoriesViewModel = hiltViewModel()
    HiltMVVMFoodCategoriesScreen(
        viewModel = viewModel,
        state = viewModel.state,
        effectFlow = viewModel.effects.receiveAsFlow(),
        onNavigationRequested = { itemId ->
            navController.navigate("${NavigationKeys.Route.FOOD_CATEGORIES_LIST}/${itemId}")
        })
}

@Composable
private fun FoodCategoryDetailsDestination() {
    val viewModel: HiltMVVMFoodCategoryDetailsViewModel = hiltViewModel()
    HiltMVVMFoodCategoryDetailsScreen(viewModel.state)
}

