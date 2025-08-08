package com.example.my_app_android.example_image_picker

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun AnimatedImageCard(
    uri: Uri,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(400))
    ) {
        Card(
            modifier = modifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { onClick() })
                },
            shape = MaterialTheme.shapes.medium
        ) {
            AsyncImage(
                model = uri,
                contentDescription = "Image Preview",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}