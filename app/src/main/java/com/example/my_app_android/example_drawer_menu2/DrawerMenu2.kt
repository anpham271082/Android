package com.example.my_app_android.example_drawer_menu2

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.my_app_android.R
import kotlinx.coroutines.launch

@Composable
fun DrawerMenu2(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    onDestinationClicked: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            // Avatar và thông tin user
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_app_icon), // ảnh trong drawable
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Xin chào,", style = MaterialTheme.typography.labelSmall)
                    Text("Pham Ngoc An", style = MaterialTheme.typography.titleMedium)
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            val items = listOf(
                DrawerItem("🏠 Trang chủ", "home"),
                DrawerItem("📄 Tài liệu", "docs"),
                DrawerItem("⚙️ Cài đặt", "settings"),
                DrawerItem("❓ Trợ giúp", "help")
            )

            items.forEach { item ->
                NavigationDrawerItem(
                    label = { Text(item.title) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onDestinationClicked(item.route)
                    },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    }
}

data class DrawerItem(val title: String, val route: String)