package com.example.my_app_android.example_mvvm2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.my_app_android.example_mvvm2.viewmodel.ExampleMVVM2MainViewModel

@Composable
fun ExampleMVVM2MainScreen (modifier: Modifier = Modifier, viewModel: ExampleMVVM2MainViewModel) {
    val userData = viewModel.userData.observeAsState()
    val isLoading = viewModel.isLoading.observeAsState()
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button (onClick = {
            viewModel.getUserData()
        }) {
            Text(text = "Get Data")
        }
        if (isLoading.value == true){
            CircularProgressIndicator()
        }else{
            userData.value?.name?.let {
                Text(text = "NAME $it")
            }
            userData.value?.age?.let {
                Text(text = "Age $it")
            }
        }
    }
}
