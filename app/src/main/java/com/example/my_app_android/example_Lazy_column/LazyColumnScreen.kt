package com.example.my_app_android.example_Lazy_column

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.my_app_android.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay


// 1. Data holder cho swipe state per row
data class SwipeRowState(val animatable: Animatable<Float, *> = Animatable(0f))

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyColumnScreen() {
    var items by remember { mutableStateOf(List(20) { i -> "User $i" }) }
    var isRefreshing by remember { mutableStateOf(false) }

    val swipeStates = remember { mutableStateMapOf<Int, SwipeRowState>() }
    val refreshState = rememberSwipeRefreshState(isRefreshing)

    SwipeRefresh(
        state = refreshState,
        onRefresh = { isRefreshing = true },
        indicator = { state, triggerDp ->
            StyleRefreshIndicator(state = state, triggerDp = triggerDp)
        }
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)) {
            /*item {
                Text(
                    text = "User List",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }*/

            itemsIndexed(items, key = { index, _ -> index }) { index, item ->
                val rowState = swipeStates.getOrPut(index) { SwipeRowState() }

                SwipeableRow(
                    avatarRes = R.drawable.strasbourg,
                    title = item,
                    description = "$item is a top contributor...",
                    date = "Aug 6, 2025",
                    swipeState = rowState,
                    onEdit = { println("Edit row: $index") },
                    onDelete = { println("Delete row: $index") }
                )
            }
        }
    }

    // Simulate refresh delay
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(2000)
            isRefreshing = false
        }
    }
}






