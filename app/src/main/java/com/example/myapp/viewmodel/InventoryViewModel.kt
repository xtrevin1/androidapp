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

    private val pageSize = 25
    private var currentPage = 0
    private var allFetched = false

    private val repository = InventoryRepository()

    var productDetails by mutableStateOf<List<ProductDetail>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    // Initiates the search and fetches product details
    fun fetchProducts(criteria: SearchRequest) {
        productDetails = emptyList()
        currentPage = 0
        allFetched = false
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

    fun loadNextPage() {
        if (isLoading || allFetched || gettingIds.isEmpty()) return

        viewModelScope.launch {
            isLoading = true
            try {
                val start = currentPage * pageSize
                val end = minOf(start + pageSize, gettingIds.size)

                if (start >= end) {
                    allFetched = true
                    return@launch
                }

                val pageIds = gettingIds.subList(start, end)
                val listIds = pageIds.map {
                    GetListRequestItem(DSInventoryLookupID = it, InvokingCRMID = 12)
                }

                val nextProducts = repo.getProductDetails(listIds)
                productDetails = productDetails + nextProducts
                currentPage++
            } catch (e: Exception) {
                Log.e("Pagination", "Failed: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}

