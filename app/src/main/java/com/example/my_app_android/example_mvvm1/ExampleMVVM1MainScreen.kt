package com.example.my_app_android.example_mvvm1

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExampleMVVM1MainScreen(viewModel: ExampleMVVM1MainViewModel) {
    val quote by viewModel.quote.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Log.d("response",quote)
        Text(text = "Quote: $quote")
        Button(onClick = { viewModel.fetchQuote() }) {
            Text("Fetch New Quote")
        }
    }
}