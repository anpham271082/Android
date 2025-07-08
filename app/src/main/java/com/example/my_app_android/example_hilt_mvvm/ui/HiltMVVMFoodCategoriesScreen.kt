package com.example.my_app_android.example_hilt_mvvm.ui

import com.example.my_app_android.R
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.CircularProgressIndicator
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.my_app_android.example_hilt_mvvm.data.model.HiltMVVMFoodItemModel
import com.example.my_app_android.example_hilt_mvvm.viewmodel.HiltMVVMFoodCategoriesViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@ExperimentalCoilApi
@Composable
fun HiltMVVMFoodCategoriesScreen(
    viewModel: HiltMVVMFoodCategoriesViewModel,
    state: HiltMVVMFoodCategoriesContract.State,
    effectFlow: Flow<HiltMVVMFoodCategoriesContract.Effect>?,
    onNavigationRequested: (itemId: String) -> Unit
) {
    val darkMode = viewModel.isDarkMode.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Listen for side effects from the VM
    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->
            if (effect is HiltMVVMFoodCategoriesContract.Effect.DataWasLoaded)
                snackbarHostState.showSnackbar(
                    message = "Food categories are loaded.",
                    duration = SnackbarDuration.Short
                )
        }?.collect()
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // Pass the SnackbarHostState here
        topBar = { CategoriesAppBar() },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dark Mode", modifier = Modifier.weight(1f))
                Switch(
                    checked = darkMode.value,
                    onCheckedChange = { viewModel.toggleDarkMode() }
                )
            }
        }
        Box(modifier = Modifier.padding(PaddingValues(
            top = innerPadding.calculateTopPadding() + 80.dp,
            start = 15.dp,
            end = 15.dp,
            bottom = 15.dp
        ))) {

            FoodCategoriesList(foodItems = state.categories) { itemId ->
                onNavigationRequested(itemId)
            }


            if (state.isLoading) {
                LoadingBar()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriesAppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Home,
                modifier = Modifier.padding(horizontal = 12.dp),
                contentDescription = "Action icon"
            )
        },
        title = { Text(stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background // Màu nền
        )
    )
}

@Composable
fun FoodCategoriesList(
    foodItems: List<HiltMVVMFoodItemModel>,
    onItemClicked: (id: String) -> Unit = { }
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom= 10.dp)
    ) {
        items(foodItems) { item ->
            FoodItemRow(item = item, itemShouldExpand = true, onItemClicked = onItemClicked)
        }
    }
}

@Composable
fun FoodItemRow(
    item: HiltMVVMFoodItemModel,
    itemShouldExpand: Boolean = false,
    iconTransformationBuilder: ImageRequest.Builder.() -> Unit = { },
    onItemClicked: (id: String) -> Unit = { }
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onItemClicked(item.id) }
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        Row(modifier = Modifier.animateContentSize()) {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                FoodItemThumbnail(item.thumbnailUrl, iconTransformationBuilder)
            }
            FoodItemDetails(
                item = item,
                expandedLines = if (expanded) 10 else 2,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 24.dp,
                        bottom = 24.dp
                    )
                    .fillMaxWidth(0.80f)
                    .align(Alignment.CenterVertically)
            )
            if (itemShouldExpand)
                Box(
                    modifier = Modifier
                        .align(if (expanded) Alignment.Bottom else Alignment.CenterVertically)
                        .noRippleClickable { expanded = !expanded }
                ) {
                    ExpandableContentIcon(expanded)
                }
        }
    }
}

@Composable
private fun ExpandableContentIcon(expanded: Boolean) {
    Icon(
        imageVector = if (expanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown,
        contentDescription = "Expand row icon",
        modifier = Modifier
            .padding(all = 16.dp)
    )
}

@Composable
fun FoodItemDetails(
    item: HiltMVVMFoodItemModel?,
    expandedLines: Int,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = item?.name ?: "",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (item?.description?.trim()?.isNotEmpty() == true)
            Text(
                text = item.description.trim(),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = expandedLines,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.alpha(0.6f)
            )
    }
}

@Composable
fun FoodItemThumbnail(
    thumbnailUrl: String,
    iconTransformationBuilder: ImageRequest.Builder.() -> Unit
) {
    Image(
        painter = rememberImagePainter(
            data = thumbnailUrl,
            builder = iconTransformationBuilder
        ),
        modifier = Modifier
            .size(88.dp)
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
        contentDescription = "Food item thumbnail picture",
    )
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

