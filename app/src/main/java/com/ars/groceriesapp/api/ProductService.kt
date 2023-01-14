package com.ars.groceriesapp.api

import com.ars.groceriesapp.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {


    @GET("/products/{id}")
    suspend fun retrieveProduct(@Path("id") id: Int): Product

    @GET("/products")
    suspend fun retrieveAllProducts(): List<Product>

    @GET("/products/exclusive")
    suspend fun retrieveExclusiveProducts(): List<Product>

}