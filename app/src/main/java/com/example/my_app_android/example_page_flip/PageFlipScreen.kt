package com.example.my_app_android.example_page_flip

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import kotlin.math.abs

data class Article(
    val title: String,
    val subtitle: String
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PageFlipScreen() {
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()

    val articles = listOf(
        Article("SwiftUI Animations", "Learn step-by-step"),
        Article("UI Design Patterns", "Updated for 2025"),
        Article("Top 10 Android Tricks", "With full code"),
        Article("Performance Boost", "Best for pro devs")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = articles.size,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 32.dp),
            itemSpacing = 16.dp
        ) { page ->

            val pageOffset = calculateCurrentOffsetForPage(page).coerceIn(-1f, 1f)
            val rotationAngleY = pageOffset * 90f

            val alphaValue = if (abs(rotationAngleY) > 90f) 0f else 1f

            Surface(
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 12.dp,
                modifier = Modifier
                    .graphicsLayer {
                        cameraDistance = 12 * density
                        rotationY = rotationAngleY
                        alpha = alphaValue
                    }
                    .fillMaxWidth()
                    .height(420.dp)
            ) {
                Box {
                    PageContent(
                        title = articles[page].title,
                        subtitle = articles[page].subtitle
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Black.copy(alpha = 0.4f * (1 - abs(pageOffset))), Color.Transparent)
                                )
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                enabled = pagerState.currentPage > 0,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            ) {
                Text("Previous")
            }

            Button(
                enabled = pagerState.currentPage < pagerState.pageCount - 1,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun PageContent(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = subtitle,
            fontSize = 18.sp,
            color = Color.DarkGray
        )
    }
}