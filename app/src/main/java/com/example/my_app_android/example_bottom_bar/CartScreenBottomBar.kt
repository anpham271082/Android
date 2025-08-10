package com.example.my_app_android.example_bottom_bar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CartScreenBottomBar() {
    var itemCount by remember { mutableStateOf(9) }
    var totalPrice by remember { mutableStateOf(999.99) }

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFFC371), Color(0xFFFF5F6D))
    )

    // Dùng rememberInfiniteTransition tạo animation pulse
    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF6F0))
            .padding(16.dp)
    ) {
        Text(
            text = "🛒 Giỏ Hàng",
            style = TextStyle(
                brush = gradientBrush,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (itemCount == 0) {
            Text("Giỏ hàng của bạn đang trống.", fontSize = 16.sp, color = Color.Gray)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(itemCount) { index ->
                    AnimatedCartItem(index)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Tổng tiền: $${"%.2f".format(totalPrice)}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Button(
                onClick = { /* TODO: Checkout */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6F00)
                ),
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .scale(pulseScale)
                    //.shadow(4.dp, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(
                    "Thanh Toán",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun AnimatedCartItem(index: Int) {
    val shimmerBrush = rememberShimmerBrush()

    val sampleImages = listOf(
        "https://i.imgur.com/UH3IPXw.jpg",
        "https://i.imgur.com/w5rkSIj.jpg",
        "https://i.imgur.com/oYiTqum.jpg",
    )

    val imageUrl = sampleImages[index % sampleImages.size]

    val offsetY = remember { Animatable(50f) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(index * 100L)
        launch {
            offsetY.animateTo(0f, tween(500))
        }
        launch {
            alphaAnim.animateTo(1f, tween(500))
        }
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationY = offsetY.value
                alpha = alphaAnim.value
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Sản phẩm #${index + 1}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                // Overlay shimmer brush hiệu ứng chạy ngang
                Spacer(
                    modifier = Modifier
                        .matchParentSize()
                        .background(shimmerBrush)
                        .alpha(0.2f)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("Sản phẩm #${index + 1}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Mô tả sản phẩm...", fontSize = 14.sp, color = Color.Gray)
            }

            Text("$99.99", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun rememberShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    return Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.1f),
            Color.White.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.1f)
        ),
        start = Offset(translateAnim.value - 200f, 0f),
        end = Offset(translateAnim.value, 0f)
    )
}