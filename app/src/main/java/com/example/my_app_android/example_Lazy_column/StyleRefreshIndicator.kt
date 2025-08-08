package com.example.my_app_android.example_Lazy_column

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun StyleRefreshIndicator(
    state: SwipeRefreshState,
    triggerDp: Dp
) {
    val density = LocalDensity.current
    val triggerPx = with(density) { triggerDp.toPx() }
    val offsetPx = state.indicatorOffset
    val progress = (offsetPx / triggerPx).coerceIn(0f, 1f)

    if (offsetPx <= 0f && !state.isRefreshing) return

    val statusText = when {
        state.isRefreshing -> "Refreshing..."
        progress >= 1f -> "Release to refresh"
        else -> "Pull to refresh"
    }

    val easedProgress = FastOutSlowInEasing.transform(progress)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp), // tăng cao để kéo thoải mái
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (state.isRefreshing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "Pull to refresh",
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(easedProgress * 180f),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = statusText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}