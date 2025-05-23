package com.example.myapp.data.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // Search endpoint with API key in the path and CRMID as a header
    @POST("{apiKey}/search")
    suspend fun searchProducts(
        @Path("apiKey") apiKey: String,
        @Header("CRMID") crmId: String,
        @Body request: SearchRequest
    ): SearchResponse

    // GetList endpoint with API key in the path and CRMID as a header
    @POST("{apiKey}/inventory_getlist")
    suspend fun getProductDetails(
        @Path("apiKey") apiKey: String,
        @Header("CRMID") crmId: String,
        @Body productIds: List<String>
    ): List<ProductDetail>
}

interface ApiService {
    @GET("items")
    suspend fun getItems(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("after") after: String? = null
    ): List<YourDataType>
}

interface ApiService {
    @POST("items")
    suspend fun getItems(@Body request: ApiRequest): ApiResponse
}