package com.example.myapp.data.remote

// Represents the search request payload
data class SearchRequest(
    val make: String?,
    val model: String?,
    val year: String?
)