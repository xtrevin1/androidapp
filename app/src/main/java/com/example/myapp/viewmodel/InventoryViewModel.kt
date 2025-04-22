package com.example.myapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.myapp.repository.InventoryRepository
import com.example.myapp.data.remote.ProductDetail
import com.example.myapp.data.remote.SearchRequest

class InventoryViewModel : ViewModel() {

    private val repository = InventoryRepository()

    var productDetails by mutableStateOf<List<ProductDetail>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    // Initiates the search and fetches product details
    fun fetchProducts(criteria: SearchRequest) {
        viewModelScope.launch {
            isLoading = true
            try {
                val ids = repository.searchProducts(criteria)
                productDetails = repository.getProductDetails(ids)
            } catch (e: Exception) {
                // Handle error appropriately
                productDetails = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    private val _inventoryDetails = mutableStateOf<List<InventoryItem>>(emptyList())
    val inventoryDetails: State<List<InventoryItem>> get() = _inventoryDetails

    fun fetchInventoryDetails(ids: List<String>) {
        viewModelScope.launch {
            val details = repository.getList(ids.mapNotNull { it.toIntOrNull() })
            _inventoryDetails.value = details
        }
    }
}
