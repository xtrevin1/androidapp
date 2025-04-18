package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapp.ui.InventoryScreen
import com.example.myapp.viewmodel.InventoryViewModel

class MainActivity : ComponentActivity() {
    private val viewModel = InventoryViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventoryScreen(viewModel = viewModel)
        }
    }
}
