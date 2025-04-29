package com.example.myapp.data.remote

// Represents the search request payload
data class SearchRequest(
    val make: String?,
    val model: String?,
    val year: String?
)

data class ApiRequest(
    val filters: Map<String, Any>? = null,
    val properties: List<String>,
    val cursor: String? = null
)

data class ApiResponse(
    val results: List<YourDataType>,
    val nextCursor: String?,
    val hasMore: Boolean
)