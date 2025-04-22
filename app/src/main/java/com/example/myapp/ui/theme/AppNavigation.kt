package com.example.myapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.myapp.ui.search.SearchScreen
import com.example.myapp.ui.results.ResultsScreen
import com.google.gson.Gson

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "search") {
        composable("search") {
            SearchScreen(navController)
        }
        composable(
            "results/{ids}",
            arguments = listOf(navArgument("ids") { type = NavType.StringType })
        ) { backStackEntry ->
            val idsJson = backStackEntry.arguments?.getString("ids") ?: "[]"
            val ids = Gson().fromJson(idsJson, Array<String>::class.java).toList()
            ResultsScreen(ids)
        }
    }
}
