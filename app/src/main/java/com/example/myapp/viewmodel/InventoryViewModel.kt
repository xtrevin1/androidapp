@HiltViewModel
class ListViewModel @Inject constructor(
    private val repo: InventoryRepositoryImpl
) : ViewModel() {

    private var currentPage = 0
    private val pageSize = 25
    private var allFetched = false

    var isLoading by mutableStateOf(false)
        private set

    var productDetails by mutableStateOf<List<DSCRMINventory>>(emptyList())
        private set

    var gettingIds: List<Long> = emptyList() // Should be set before paging begins

    fun setIds(ids: List<Long>) {
        gettingIds = ids
        currentPage = 0
        allFetched = false
        productDetails = emptyList()
    }

    fun loadNextPage() {
        if (isLoading || allFetched) return

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
                    DSCRMINventory(dsInventoryLookupID = it, invokingCRMID = 12)
                }

                val nextProducts = repo.inventoryGetList(listIds)
                productDetails = productDetails + nextProducts
                currentPage++
            } catch (e: Exception) {
                Log.e("ListViewModel", "Failed: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: InventoryRepositoryImpl
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var productDetails by mutableStateOf<List<DSCRMINventory>>(emptyList())
        private set

    var gettingIds by mutableStateOf<List<Long>>(emptyList())
        private set

    private var currentPage = 0
    private val pageSize = 25
    private var allFetched = false

    fun searchProducts(criteria: SandhillsSearch) {
        viewModelScope.launch {
            isLoading = true
            try {
                gettingIds = repo.searchInventory(criteria)
                productDetails = emptyList()
                currentPage = 0
                allFetched = false
                loadNextPage()
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Search failed: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun loadNextPage() {
        if (isLoading || allFetched) return

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
                    DSCRMINventory(dsInventoryLookupID = it, invokingCRMID = 12)
                }

                val nextProducts = repo.inventoryGetList(listIds)
                productDetails = productDetails + nextProducts
                currentPage++
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Paging failed: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}
