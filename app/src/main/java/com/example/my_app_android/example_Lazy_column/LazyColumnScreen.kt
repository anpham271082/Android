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

    var refreshKey by remember { mutableStateOf(0) }

    var deleteDialogIndex by remember { mutableStateOf<Int?>(null) }

    SwipeRefresh(
        state = refreshState,
        onRefresh = { isRefreshing = true },
        indicator = { state, triggerDp ->
            StyleRefreshIndicator(state = state, triggerDp = triggerDp)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
        ) {
            /*item {
                Text(
                    text = "User List",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }*/

            itemsIndexed(
                items,
                key = { _, item -> "$refreshKey-$item" } // 🔹 key thay đổi khi refresh
            ) { index, item ->
                val rowState = swipeStates.getOrPut(item.hashCode()) { SwipeRowState() }

                SwipeableRow(
                    avatarRes = R.drawable.strasbourg,
                    title = item,
                    description = "$item is a top contributor...",
                    date = "Aug 6, 2025",
                    swipeState = rowState,
                    onEdit = { println("Edit row: $index") },
                    onDelete = { deleteDialogIndex = index }
                )
            }
        }
    }

    // Dialog Delete
    if (deleteDialogIndex != null) {
        val user = items.getOrNull(deleteDialogIndex!!)
        if (user != null) {
            DeleteUserDialog(
                user = user,
                onDismiss = { deleteDialogIndex = null },
                onConfirm = {
                    val removedUser = items[deleteDialogIndex!!]
                    items = items.toMutableList().also {
                        it.removeAt(deleteDialogIndex!!)
                    }
                    swipeStates.remove(removedUser.hashCode())
                    deleteDialogIndex = null
                }
            )
        }
    }

    // Simulate refresh delay
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(2000)
            // 🔹 Reset lại danh sách ban đầu
            items = List(20) { i -> "User $i" }

            // 🔹 Xoá hết swipe states để đóng tất cả hàng
            swipeStates.clear()

            refreshKey++
            isRefreshing = false
        }
    }
}

@Composable
fun DeleteUserDialog(
    user: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Delete User", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.error)
        },
        text = {
            Text("Are you sure you want to delete \"$user\"? This action cannot be undone.")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        shape = MaterialTheme.shapes.medium
    )
}