package com.example.my_app_android.example_bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreenBottomBar() {
    var searchText by remember { mutableStateOf("") }
    val sampleResults = listOf(
        "Kết quả tìm kiếm 1",
        "Kết quả tìm kiếm 2",
        "Kết quả tìm kiếm 3",
        "Kết quả tìm kiếm 4"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color(0xFFF5F0FF))
    ) {
        Text(
            text = "Tìm Kiếm",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        androidx.compose.material3.TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Nhập từ khóa...", color = Color(0xFF9E9E9E)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFF6A4FFF).copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF6A4FFF),
                focusedTextColor = Color(0xFF1B1B1B),
                unfocusedTextColor = Color(0xFF1B1B1B)
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Kết quả tìm kiếm:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val filteredResults = sampleResults.filter {
            it.contains(searchText, ignoreCase = true) || searchText.isEmpty()
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredResults, key = { it }) { result ->
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { visible = true }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(400)) + scaleIn(
                        animationSpec = spring(dampingRatio = 0.5f, stiffness = 200f),
                        initialScale = 0.5f
                    ),
                    exit = fadeOut()
                ) {
                    // Tạo animation xoay khi hiện ra
                    val rotation by animateFloatAsState(
                        targetValue = if (visible) 0f else -15f,
                        animationSpec = tween(durationMillis = 500)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                rotationZ = rotation
                            }
                            .shadow(6.dp, RoundedCornerShape(14.dp)),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = result,
                            modifier = Modifier.padding(20.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3E3E3E)
                        )
                    }
                }
            }
        }
    }
}