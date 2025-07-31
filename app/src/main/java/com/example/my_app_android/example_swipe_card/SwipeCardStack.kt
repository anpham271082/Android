package com.example.my_app_android.example_swipe_card

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalDragOrCancellation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch
import coil.compose.AsyncImage

@Composable
fun SwipeCardStack(
    items: List<String>,
    onAllCardsSwiped: () -> Unit
) {
    var cardIndex by remember { mutableStateOf(0) }
    val maxVisibleCards = 3

    val context = LocalContext.current
    val swipeThreshold = 300f
    val coroutineScope = rememberCoroutineScope()

    if (cardIndex >= items.size) {
        onAllCardsSwiped()
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        for (i in (cardIndex until (cardIndex + maxVisibleCards)).reversed()) {
            if (i >= items.size) continue

            val offsetX = remember { Animatable(0f) }

            val scale = if (i == cardIndex) 1f else 0.95f - (i - cardIndex) * 0.02f
            val rotation = if (i == cardIndex) (offsetX.value / 60).coerceIn(-30f, 30f) else 0f
            val cardAlpha = if (i == cardIndex) 1f else 0.9f


            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .graphicsLayer {
                        translationX = if (i == cardIndex) offsetX.value else 0f
                        scaleX = scale
                        scaleY = scale
                        rotationZ = rotation
                        alpha = cardAlpha
                    }
                    .fillMaxWidth()
                    .height(450.dp)
                    .align(Alignment.Center)
                    .zIndex((items.size - i).toFloat())
                    .pointerInput(i) {
                        if (i == cardIndex) {
                            detectHorizontalDragGestures(
                                onDragEnd = {
                                    coroutineScope.launch {
                                        when {
                                            offsetX.value > swipeThreshold -> {
                                                offsetX.animateTo(1000f)
                                                offsetX.snapTo(0f)
                                                cardIndex++
                                            }
                                            offsetX.value < -swipeThreshold -> {
                                                offsetX.animateTo(-1000f)
                                                offsetX.snapTo(0f)
                                                cardIndex++
                                            }
                                            else -> {
                                                offsetX.animateTo(0f, tween(300))
                                            }
                                        }
                                    }
                                },
                                onHorizontalDrag = { _, dragAmount ->
                                    coroutineScope.launch {
                                        offsetX.snapTo(offsetX.value + dragAmount)
                                    }
                                }
                            )
                        }
                    },
                colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {
                // Load image with Coil or basic Painter
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AsyncImage(
                        model = items[i],
                        contentDescription = "Swipe card image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Overlay gradient & text
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xAA000000)),
                                    startY = 100f
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Card #${i + 1}",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "This is a beautiful description for the image.",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}