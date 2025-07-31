package com.example.my_app_android.example_swipe_card

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun SwipeCardScreen() {
    val context = LocalContext.current
    val cardImages = listOf(
        "https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg",
        "https://images.pexels.com/photos/2100063/pexels-photo-2100063.jpeg",
        "https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg",
        "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg",
        "https://images.pexels.com/photos/145939/pexels-photo-145939.jpeg"
    )

    SwipeCardStack(items = cardImages) {
        Toast.makeText(context, "Đã hết thẻ!", Toast.LENGTH_SHORT).show()
    }
}