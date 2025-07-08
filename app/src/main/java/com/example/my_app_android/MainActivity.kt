package com.example.my_app_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.my_app_android.example_hilt_mvvm.HiltMVVMNavGraph
import com.example.my_app_android.example_mvvm2.viewmodel.ExampleMVVM2MainViewModel
import com.example.my_app_android.example_room_mvvm.ui.RoomMVVMNoteScreen
import com.example.my_app_android.ui.theme.My_app_androidTheme

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private val viewModel: ExampleMVVM1MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = ViewModelProvider(this)[ExampleMVVM2MainViewModel::class]
        enableEdgeToEdge()
        setContent {
            /*My_app_androidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.LightGray
                ){ innerPadding->
                    ExampleLoginRegisterNavGraph(innerPadding)
                }
            }*/

            //ExampleMVVM1MainScreen(viewModel)

            //ExampleMVVM2MainScreen(modifier = Modifier.padding(0.dp), mainViewModel)

            /*My_app_androidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.LightGray
                ){
                    ExampleMVVM3PostListScreen(paddingValues = it)
                }
            }*/

            //ExampleNavigation()

            //HiltMVVMNavGraph()

            RoomMVVMNoteScreen()

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    My_app_androidTheme {
        Greeting("Android")
    }
}


