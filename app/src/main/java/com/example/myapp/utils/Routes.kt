sealed class Screen(val route: String) {
    object Search : Screen("search")
    object List : Screen("list?ids={ids}")
}

// navController.navigate("list?ids=123,456,789")

/*
NavHost(
    navController = navController,
    startDestination = Screen.Search.route
) {
    composable(Screen.Search.route) {
        SearchScreen(navController)
    }

    composable(
        route = Screen.List.route,
        arguments = listOf(navArgument("ids") {
            type = NavType.StringType
            nullable = true
        })
    ) { backStackEntry ->
        ListScreen(
            navController = navController,
            idsArg = backStackEntry.arguments?.getString("ids")
        )
    }
}
 */

/* In Search screen
val idsString = viewModel.gettingIds.joinToString(",")
navController.navigate("list?ids=${Uri.encode(idsString)}")
 */

/*
@Composable
fun ListScreen(
    navController: NavController,
    idsArg: String?,
    viewModel: ListViewModel = hiltViewModel()
) {
    val decodedIds = remember(idsArg) {
        idsArg?.split(",")?.mapNotNull { it.toLongOrNull() } ?: emptyList()
    }

    // Only call setIds once
    LaunchedEffect(Unit) {
        if (decodedIds.isNotEmpty()) {
            viewModel.setIds(decodedIds)
        }
    }

    val products = viewModel.productDetails
    val isLoading = viewModel.isLoading

    LazyColumn {
        itemsIndexed(products) { index, item ->
            ProductItem(item)

            if (index >= products.lastIndex - 3 && !isLoading) {
                viewModel.loadNextPage()
            }
        }

        if (isLoading) {
            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
 */