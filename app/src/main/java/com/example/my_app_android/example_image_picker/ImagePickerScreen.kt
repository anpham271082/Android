import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.my_app_android.example_image_picker.AnimatedImageCard
import com.example.my_app_android.example_image_picker.MultiImageItem
import com.example.my_app_android.example_image_picker.SingleImageCard
import com.example.my_app_android.example_image_picker.ZoomableImageOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickerScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageUriList by remember { mutableStateOf<List<Uri>>(emptyList()) }

    var zoomImageUri by remember { mutableStateOf<Uri?>(null) }

    val singleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> if (uri != null) selectedImageUri = uri }
    )
    val multipleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uriList -> selectedImageUriList = uriList }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    singleImagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
                Text("Pick Single Image")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    multipleImagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
                BadgedBox(
                    badge = {
                        if (selectedImageUriList.isNotEmpty()) {
                            Badge {
                                Text(text = selectedImageUriList.size.toString())
                            }
                        }
                    }
                ) {
                    Text("Pick Multiple Images")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Selected Single Image:", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                SingleImageCard(
                    uri = selectedImageUri!!,
                    onRemove = { selectedImageUri = null },
                    modifier = Modifier.fillMaxSize(),
                    onClick = { zoomImageUri = selectedImageUri }
                )
            } else {
                Text(
                    "No Image Selected",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Selected Multiple Images (${selectedImageUriList.size}):", style = MaterialTheme.typography.titleMedium)
        if (selectedImageUriList.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(vertical = 8.dp)
            ) {
                items(selectedImageUriList) { uri ->
                    MultiImageItem(
                        uri,
                        onRemove = {
                            selectedImageUriList = selectedImageUriList.filter { it != uri }
                        },
                        onClick = {
                            zoomImageUri = uri
                        }
                    )
                }
            }
        } else {
            Text(
                "No Images Selected",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("All Selected Images Preview:", style = MaterialTheme.typography.titleMedium)

        val allUris by remember {
            derivedStateOf {
                listOfNotNull(selectedImageUri) + selectedImageUriList
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(allUris, key = { index, uri -> "${uri}_${index}" }) { index, uri ->
                AnimatedImageCard(
                    uri = uri,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (uri == selectedImageUri) 220.dp else 180.dp),
                    onClick = { zoomImageUri = uri }
                )
            }
        }
    }

    // Hiển thị overlay zoom fullscreen thay vì dialog
    if (zoomImageUri != null) {
        ZoomableImageOverlay(
            uri = zoomImageUri!!,
            onClose = { zoomImageUri = null }
        )
    }
}