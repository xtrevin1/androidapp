@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }

    val searchResults = viewModel.productDetails
    val isLoading = viewModel.isLoading

    Column(modifier = Modifier.fillMaxSize()) {

        // Search input
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search...") }
        )

        // Search button
        Button(
            onClick = {
                val criteria = SandhillsSearch(query) // or however you're structuring search
                viewModel.searchProducts(criteria)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        ) {
            Text("Search")
        }

        // Results list
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(searchResults) { index, item ->
                ProductItem(item)

                // Load more when near the end
                if (index >= searchResults.lastIndex - 3 && !isLoading) {
                    viewModel.loadNextPage()
                }
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}