package com.example.my_app_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.my_app_android.example_drawer_menu2.DrawerMenu2
import com.example.my_app_android.example_drawer_menu2.DrawerMenu2MainScreen
import com.example.my_app_android.example_hilt_mvvm.HiltMVVMNavGraph
import com.example.my_app_android.example_mvvm2.viewmodel.ExampleMVVM2MainViewModel
import com.example.my_app_android.example_slide_image.ExampleSlideImage

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private val viewModel: ExampleMVVM1MainViewModel by viewModels()


    @OptIn(ExperimentalMaterial3Api::class)
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

            //RoomMVVMNoteScreen()

            //MenuTopBottomBarMainScreen()


            //ArgumentsAppNavigation()

            //PopBackStackNavHost()

            //DrawerMenu2MainScreen()

            ExampleSlideImage(
                images = listOf(
                    "https://images.pexels.com/photos/1658967/pexels-photo-1658967.jpeg",
                    "https://images.pexels.com/photos/2896668/pexels-photo-2896668.jpeg",
                    "https://images.pexels.com/photos/3598176/pexels-photo-3598176.jpeg",
                    "https://images.pexels.com/photos/731423/pexels-photo-731423.jpeg",
                    "https://images.pexels.com/photos/1173777/pexels-photo-1173777.jpeg",
                    "https://images.pexels.com/photos/1179225/pexels-photo-1179225.jpeg"
                )
            )
        }
    }
}
