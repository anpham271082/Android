package com.example.my_app_android.example_popbackstack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PopBackStackProfileScreen(navController: NavController) {
    val currentBackStackEntry = navController.previousBackStackEntry
    val savedStateHandle = currentBackStackEntry?.savedStateHandle

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile Screen")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            savedStateHandle?.set("selected_name", "Pham Ngoc An")
            navController.popBackStack()
        }) {
            Text("Select 'Pham Ngoc An'")
        }
    }
}
