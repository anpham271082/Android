package com.example.my_app_android.example_popbackstack

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun PopBackStackHomeScreen(navController: NavController, backStackEntry: NavBackStackEntry) {
    val savedStateHandle = backStackEntry.savedStateHandle

    val selectedName by savedStateHandle.getStateFlow("selected_name", "")
        .collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selected name: $selectedName")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("profile")
        }) {
            Text("Go to Profile")
        }
    }
}
