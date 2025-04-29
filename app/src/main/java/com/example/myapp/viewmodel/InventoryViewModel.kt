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
    private val paginationType = PaginationType.PAGE_BASED // or change later

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

    private var currentPage = 0
    private var currentOffset = 0
    private var lastItemId: String? = null

    private val pageSize = 20

    fun loadNextItems() {
        if (isLoading || endReached) return

        isLoading = true

        viewModelScope.launch {
            val result = when (paginationType) {
                PaginationType.PAGE_BASED -> repository.getItemsByPage(currentPage, pageSize)
                PaginationType.OFFSET_BASED -> repository.getItemsByOffset(currentOffset, pageSize)
                PaginationType.CURSOR_BASED -> repository.getItemsByCursor(lastItemId, pageSize)
            }

            if (result.isNotEmpty()) {
                items.addAll(result)

                // Update pagination cursor
                when (paginationType) {
                    PaginationType.PAGE_BASED -> currentPage++
                    PaginationType.OFFSET_BASED -> currentOffset += pageSize
                    PaginationType.CURSOR_BASED -> lastItemId = result.lastOrNull()?.id
                }
            } else {
                endReached = true
            }

            isLoading = false
        }
    }

    suspend fun getItemsByPage(page: Int, size: Int): List<YourDataType> {
        return apiService.getItems(page = page, size = size)
    }

    suspend fun getItemsByOffset(offset: Int, limit: Int): List<YourDataType> {
        return apiService.getItems(offset = offset, limit = limit)
    }

    suspend fun getItemsByCursor(cursor: String?, limit: Int): List<YourDataType> {
        return apiService.getItems(after = cursor, limit = limit)
    }

}


class YourViewModel(private val repository: YourRepository) : ViewModel() {
    var items = mutableStateListOf<YourDataType>()
        private set

    var isLoading by mutableStateOf(false)
    var hasMore by mutableStateOf(true)

    private var nextCursor: String? = null

    fun loadNextPage() {
        if (isLoading || !hasMore) return

        isLoading = true

        viewModelScope.launch {
            val response = repository.getItems(
                filters = null, // or your filter logic
                properties = listOf("name", "email"), // or your real properties
                cursor = nextCursor
            )

            items.addAll(response.results)
            nextCursor = response.nextCursor
            hasMore = response.hasMore
            isLoading = false
        }
    }
}