package com.ars.data.remote.api

import com.ars.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("/products/{id}")
    suspend fun retrieveProduct(@Path("id") id: Int): ProductResponse

    @GET("/products")
    suspend fun retrieveAllProducts(): List<ProductResponse>

    @GET("/products/exclusive")
    suspend fun retrieveExclusiveProducts(): List<ProductResponse>
}