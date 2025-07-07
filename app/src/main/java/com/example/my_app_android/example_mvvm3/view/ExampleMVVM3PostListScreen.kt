
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.my_app_android.example_mvvm3.data.api.model.ExampleMVVM3PostData
import com.example.my_app_android.example_mvvm3.viewmodel.ExampleMVVM3PostViewModel
import com.example.my_app_android.ui.theme.PurpleGrey40

@Composable
fun ExampleMVVM3PostListScreen(viewModel: ExampleMVVM3PostViewModel = ExampleMVVM3PostViewModel(),
                               paddingValues: PaddingValues) {
    val posts by viewModel.posts
    if (posts.isEmpty()) {
        CenteredCircularProgressIndicator()
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(posts) {
                PostCard(it)
            }
        }
    }
}
@Composable
fun PostCard(post: ExampleMVVM3PostData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(PurpleGrey40),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "post # ${post.id}", style = MaterialTheme.typography.bodyLarge)
            Text(text = post.title, style = MaterialTheme.typography.bodyMedium)
            Text(text = post.body, style = MaterialTheme.typography.bodySmall)
        }
    }
}
@Composable
fun CenteredCircularProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}