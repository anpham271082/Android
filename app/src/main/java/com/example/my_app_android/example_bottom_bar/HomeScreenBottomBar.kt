package com.example.my_app_android.example_bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay

data class Category(val name: String, val icon: ImageVector, val color: Color)
data class BestSeller(val name: String, val description: String, val imageUrl: String)

@Composable
fun HomeScreenBottomBar() {
    val bestSellers = listOf(
        BestSeller(
            "Áo thun nam",
            "Áo thun cotton cao cấp, thoáng mát",
            "https://i.imgur.com/UH3IPXw.jpg"
        ),
        BestSeller(
            "Tai nghe Bluetooth",
            "Chất lượng âm thanh sống động",
            "https://i.imgur.com/w5rkSIj.jpg"
        ),
        BestSeller(
            "Giày thể thao",
            "Phù hợp mọi hoạt động thể thao",
            "https://i.imgur.com/oYiTqum.jpg"
        )
    )

    val categories = listOf(
        Category("Thời trang", Icons.Default.Home, Color(0xFF957DAD)),
        Category("Điện tử", Icons.Default.ChildCare, Color(0xFFB39DDB)),
        Category("Sách", Icons.Default.Book, Color(0xFFC5CAE9)),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF8F6FF)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Các danh mục nổi bật",
            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color(0xFF5E35B1),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Thay thế Row thông thường bằng hàm AnimatedCategoryRow có animation
        AnimatedCategoryRow(categories = categories)

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            "Sản phẩm bán chạy",
            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
            fontSize = 22.sp,
            color = Color(0xFF5E35B1),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(bestSellers) { item ->
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    visible = true
                }
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(500)) + slideInHorizontally(animationSpec = tween(500)) { fullWidth -> fullWidth },
                    exit = fadeOut()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(item.imageUrl),
                                contentDescription = item.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(item.name, fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFF4527A0))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(item.description, fontSize = 14.sp, color = Color(0xFF6A1B9A))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedCategoryRow(categories: List<Category>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        categories.forEachIndexed { index, category ->

            var visible by remember { mutableStateOf(false) }
            var animationStarted by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                delay(index * 150L)
                visible = true
                delay(600) // đợi animation hiện ban đầu xong
                animationStarted = true
            }

            val infiniteTransition = rememberInfiniteTransition()

            // Animation loop xoay Y (-10 -> +10 độ)
            val _rotationY by infiniteTransition.animateFloat(
                initialValue = -5f,
                targetValue = 5f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1800, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            // Animation loop lắc ngang ±8 dp
            val _translationX by infiniteTransition.animateFloat(
                initialValue = -5f,
                targetValue = 5f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1800, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            // Animation loop alpha nhấp nháy 0.85 - 1.0
            val _alphaLoop by infiniteTransition.animateFloat(
                initialValue = 0.85f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1800, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            // Animation xuất hiện ban đầu fade + translateY
            val alphaAnim by animateFloatAsState(
                targetValue = if (visible) 1f else 0f,
                animationSpec = tween(500)
            )
            val offsetYPx by animateFloatAsState(
                targetValue = if (visible) 0f else 40f,
                animationSpec = tween(500)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(110.dp)
                    .graphicsLayer {
                        // Chuyển animation tùy trạng thái:
                        if (animationStarted) {
                            //alpha = _alphaLoop
                            //translationX = _translationX.dp.toPx()
                            rotationY = _rotationY
                        } else {
                            //alpha = alphaAnim
                            //translationY = offsetYPx
                            rotationY = 0f
                            translationX = 0f
                        }
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                category.color.copy(alpha = 0.15f),
                                category.color.copy(alpha = 0.05f)
                            )
                        )
                    )
                    .shadow(6.dp, RoundedCornerShape(12.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = category.name,
                        tint = category.color.copy(alpha = 0.85f),  // Đậm hơn một chút, hơi trong suốt
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = category.name,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        fontSize = 16.sp,
                        color = category.color.copy(alpha = 0.9f),  // Đậm, dễ đọc
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}