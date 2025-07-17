package com.example.my_app_android.project.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// -----------------------------
// 🔹 Toast-like Snackbar
// -----------------------------

fun showSnackbar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    scope.launch {
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = duration
        )
    }
}

// -----------------------------
// 🔹 Basic Center Loader
// -----------------------------

@Composable
fun CenteredProgressBar(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

// -----------------------------
// 🔹 Empty State UI
// -----------------------------

@Composable
fun EmptyState(
    message: String,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon?.invoke()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

// -----------------------------
// 🔹 Gradient Background
// -----------------------------

fun Modifier.verticalGradientBackground(
    colors: List<Color>,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp)
): Modifier = this
    .background(brush = Brush.verticalGradient(colors), shape = shape)

// -----------------------------
// 🔹 Auto Resize Text (basic)
// -----------------------------

@Composable
fun AutoResizingText(
    text: String,
    modifier: Modifier = Modifier,
    style: androidx.compose.ui.text.TextStyle = LocalTextStyle.current,
    maxLines: Int = 1
) {
    var textSize by remember { mutableStateOf(16f) }

    Text(
        text = text,
        maxLines = maxLines,
        style = style.copy(fontSize = with(LocalDensity.current) { textSize.dp.toSp() }),
        modifier = modifier
    )
}

//val snackbarHostState = remember { SnackbarHostState() }
//val scope = rememberCoroutineScope()
//
//Button(onClick = {
//    showSnackbar(scope, snackbarHostState, "Something happened", "Undo")
//}) {
//    Text("Show Snackbar")
//}

//CenteredProgressBar()
//
//EmptyState(message = "No data found.")
//EmptyState(
//    message = "No favorites yet.",
//    icon = { Icon(Icons.Default.Star, contentDescription = null) }
//)
//
//Box(
//    modifier = Modifier
//        .fillMaxWidth()
//        .height(200.dp)
//        .verticalGradientBackground(colors = listOf(Color.Blue, Color.Cyan))
//)
//AutoResizingText(text = "Responsive title")