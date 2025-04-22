package com.example.myapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.data.remote.SearchRequest
import com.example.myapp.viewmodel.InventoryViewModel

@Composable
fun InventoryScreen(viewModel: InventoryViewModel) {
    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = make, onValueChange = { make = it }, label = { Text("Make") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Year") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val criteria = SearchRequest(make, model, year)
            viewModel.fetchProducts(criteria)
        }) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(viewModel.productDetails) { product ->
                    Text("${product.name}: ${product.description}")
                    Divider()
                }
            }
        }

        // Inside SearchScreen.kt (your button onClick or success handler)
        val idsJson = Uri.encode(Gson().toJson(idList))
        navController.navigate("results/$idsJson")
    }
}
