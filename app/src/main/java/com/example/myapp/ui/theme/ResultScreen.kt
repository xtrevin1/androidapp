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
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(item.displayName, modifier = Modifier.weight(1f))
                Text(item.condition, modifier = Modifier.weight(1f))
            }
        }
    }
}
