@Composable
fun Navigation(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = backStackEntry?.destination?.route != Routes.Search.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Android Project") },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Search.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Search.route) {
                SearchScreen(navController = navController)
            }

            composable(
                "${Routes.List.route}/{ids}"
            ) {
                val ids = Gson().fromJson(
                    it.arguments?.getString("ids"),
                    Array<Long>::class.java
                ).toList()
                ListScreen(viewModel = hiltViewModel(), idList = ids)
            }
        }
    }
}
