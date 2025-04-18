package com.example.myapp.repository

import com.example.myapp.data.model.InventoryItem
import com.example.myapp.data.remote.SearchRequest
import kotlinx.coroutines.delay

class TestInventoryRepository {
    suspend fun searchInventory(request: SearchRequest): List<InventoryItem> {
        // Simulate network delay
        delay(1000)

        // Return fake inventory
        return listOf(
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified"),
            InventoryItem("Toyota Camry 2022", 12345, "New"),
            InventoryItem("Honda Civic 2021", 23456, "Used"),
            InventoryItem("Ford F-150 2020", 34567, "Certified")
        )
    }
}
