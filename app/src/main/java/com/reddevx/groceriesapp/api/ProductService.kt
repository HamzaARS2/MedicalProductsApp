package com.reddevx.groceriesapp.api

import com.reddevx.groceriesapp.model.Product
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {

    @POST("/products/add")
    suspend fun insertProduct(@Body product: Product): Product

    @GET("/products/{id}")
    suspend fun retrieveProduct(@Path("id") id: Int): Product

    @GET("/products")
    suspend fun retrieveAllProducts(): List<Product>

    @PUT("/products/update/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: Product): Product

    @DELETE("/products/delete/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): String
}