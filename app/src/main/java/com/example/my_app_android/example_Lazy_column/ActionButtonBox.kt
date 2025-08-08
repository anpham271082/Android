package com.example.my_app_android.example_Lazy_column

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ActionButtonBox(
    modifier: Modifier = Modifier,
    color: Color,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = label, tint = Color.White)
            Text(label, color = Color.White, style = MaterialTheme.typography.labelSmall)
        }
    }
}