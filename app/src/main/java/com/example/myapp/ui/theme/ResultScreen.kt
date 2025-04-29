// ui/results/ResultsScreen.kt
package com.example.yourapp.ui.results

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourapp.viewmodel.InventoryViewModel
import com.example.yourapp.data.model.InventoryItem

@Composable
fun ResultsScreen(ids: List<String>) {
    val viewModel: InventoryViewModel = viewModel()
    val items by viewModel.inventoryDetails.collectAsState()

    LaunchedEffect(ids) {
        viewModel.fetchInventoryDetails(ids)
    }

    LazyColumn {
        items(viewModel.productDetails.size) { index ->
            val item = viewModel.productDetails[index]

            Row(modifier = Modifier.fillMaxWidth()) {
                TableCell(text = item.DisplayName, weight = columnWeight)
                TableCell(text = item.DSInventoryLookupID.toString(), weight = column2Weight)
                TableCell(text = item.Condition.toString(), weight = column3Weight)
            }

            // 🔁 Trigger next page load if we're at the bottom
            if (index == viewModel.productDetails.lastIndex && !viewModel.isLoading) {
                viewModel.loadNextPage()
            }
        }

        // Bottom loading spinner for pagination
        if (viewModel.isLoading) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun YourListScreen(viewModel: YourViewModel) {
    val list = viewModel.items
    val isLoading = viewModel.isLoading

    LazyColumn {
        itemsIndexed(list) { index, item ->
            YourItemView(item = item)

            // Trigger loading when near the end
            if (index >= list.lastIndex - 5 && !isLoading) {
                viewModel.loadNextPage()
            }
        }

        if (isLoading) {
            item {
                Box(
                    Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

LaunchedEffect(Unit) {
    viewModel.loadNextPage()
}