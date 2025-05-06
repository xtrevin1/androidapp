// --- Application class ---
@HiltAndroidApp
class MyApp : Application()

// --- ViewModel 1: SearchViewModel.kt ---
@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
var ids by mutableStateOf<List<Long>>(emptyList())
private set

    fun performSearch(query: String) {
        // Simulate fetching IDs from search
        ids = listOf(1001, 1002, 1003, 1004)
    }
}

// --- ViewModel 2: ListViewModel.kt ---
@HiltViewModel
class ListViewModel @Inject constructor() : ViewModel() {
var items by mutableStateOf<List<Long>>(emptyList())
private set

    fun setIds(ids: List<Long>) {
        items = ids
    }
}

// --- Navigation Routes ---
sealed class Screen(val route: String) {
object Search : Screen("search")
object List : Screen("list?ids={ids}")
}

// --- SearchScreen.kt ---
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
var query by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            viewModel.performSearch(query)
            val encodedIds = Uri.encode(viewModel.ids.joinToString(","))
            navController.navigate("list?ids=$encodedIds")
        }) {
            Text("Search")
        }
    }
}

// --- ListScreen.kt ---
@Composable
fun ListScreen(
navController: NavController,
idsArg: String?,
viewModel: ListViewModel
) {
val parsedIds = remember(idsArg) {
idsArg?.split(",")?.mapNotNull { it.toLongOrNull() } ?: emptyList()
}

    LaunchedEffect(parsedIds) {
        if (parsedIds.isNotEmpty()) {
            viewModel.setIds(parsedIds)
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Loaded IDs:", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        viewModel.items.forEach {
            Text("ID: $it")
        }
    }
}

// --- MainActivity.kt ---
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContent {
val navController = rememberNavController()
NavHost(navController, startDestination = Screen.Search.route) {
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
val viewModel: ListViewModel = hiltViewModel(backStackEntry)
val idsArg = backStackEntry.arguments?.getString("ids")
ListScreen(navController, idsArg, viewModel)
}
}
}
}
}

// --- build.gradle (Module) ---
plugins {
id 'com.android.application'
id 'kotlin-android'
id 'kotlin-kapt'
id 'dagger.hilt.android.plugin'
}

dependencies {
implementation "androidx.core:core-ktx:1.12.0"
implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.6.2"
implementation "androidx.activity:activity-compose:1.8.2"
implementation "androidx.compose.ui:ui:1.5.1"
implementation "androidx.navigation:navigation-compose:2.7.5"
implementation "androidx.hilt:hilt-navigation-compose:1.1.0"
implementation "com.google.dagger:hilt-android:2.48"
kapt "com.google.dagger:hilt-compiler:2.48"
}

// --- AndroidManifest.xml ---
<application
android:name=".MyApp"
... >
...
</application>
