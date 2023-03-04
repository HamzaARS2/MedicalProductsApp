package com.ars.data.remote.api

import com.ars.domain.model.Category
import com.ars.domain.model.ProductDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("categories")
    suspend fun retrieveAllCategories(): List<Category>

    @GET("categories/{id}")
    suspend fun retrieveCategory(@Path("id") id: Int): Category
}