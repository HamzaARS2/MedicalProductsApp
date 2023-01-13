package com.reddevx.groceriesapp.api

import com.reddevx.groceriesapp.data.network.Resource
import com.reddevx.groceriesapp.model.Product
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {


    @GET("/products/{id}")
    suspend fun retrieveProduct(@Path("id") id: Int): Product

    @GET("/products")
    suspend fun retrieveAllProducts(): List<Product>

    @GET("/products/exclusive")
    suspend fun retrieveExclusiveProducts(): List<Product>

}