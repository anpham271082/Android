package com.example.my_app_android.project.utils

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions

// -----------------------------
// 🔁 Simplified Navigate With Route
// -----------------------------

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        launchSingleTop = true
    }
}

fun NavController.navigateAndClearStack(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateAndClearStack.graph.findStartDestination().id) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

// -----------------------------
// 🧭 Safe Back Navigation
// -----------------------------

fun NavController.safeNavigateUp() {
    if (this.previousBackStackEntry != null) {
        this.navigateUp()
    }
}

fun NavController.safePopBackStack() {
    if (this.previousBackStackEntry != null) {
        this.popBackStack()
    }
}

// -----------------------------
// 📦 Navigation with Arguments
// -----------------------------

fun NavController.navigateWithArgs(
    route: String,
    args: Map<String, Any?>,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    val uri = Uri.Builder()
        .scheme("app")
        .authority("navigation")
        .appendPath(route)
        .apply {
            args.forEach { (key, value) ->
                appendQueryParameter(key, value?.toString())
            }
        }
        .build()

    this.navigate(uri.toString(), builder ?: {})
}

// -----------------------------
// 📍 Current Route
// -----------------------------

val NavController.currentRoute: String?
    get() = currentBackStackEntry?.destination?.route

//navController.navigateSingleTopTo("home")
//navController.navigateAndClearStack("main")
//navController.safeNavigateUp()
//navController.safePopBackStack()
//
//navController.navigateWithArgs(
//    route = "details",
//    args = mapOf("id" to 42, "title" to "Compose")
//)
//val id = navBackStackEntry.arguments?.getString("id")?.toIntOrNull()
//val title = navBackStackEntry.arguments?.getString("title")
//
//val route = navController.currentRoute
//Log.d("Nav", "Current route: $route")

//NavHost(
//    navController = navController,
//    startDestination = "home"
//) {
//    composable("home") { HomeScreen(navController) }
//    composable("details?id={id}&title={title}") { backStackEntry ->
//        val id = backStackEntry.arguments?.getString("id")
//        val title = backStackEntry.arguments?.getString("title")
//        DetailScreen(id, title)
//    }
//}