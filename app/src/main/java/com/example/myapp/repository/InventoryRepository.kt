package com.example.myapp.repository

import com.example.myapp.data.remote.ProductDetail
import com.example.myapp.data.remote.RetrofitInstance
import com.example.myapp.data.remote.SearchRequest
import com.example.myapp.utils.Constants

class InventoryRepository {

    // Performs the search operation
    suspend fun searchProducts(criteria: SearchRequest): List<String> {
        val response = RetrofitInstance.api.searchProducts(
            apiKey = Constants.API_KEY,
            crmId = Constants.CRM_ID,
            request = criteria
        )
        return response.productIds
    }

    // Retrieves detailed information for the given product IDs
    suspend fun getProductDetails(productIds: List<String>): List<ProductDetail> {
        return RetrofitInstance.api.getProductDetails(
            apiKey = Constants.API_KEY,
            crmId = Constants.CRM_ID,
            productIds = productIds
        )
    }
}
