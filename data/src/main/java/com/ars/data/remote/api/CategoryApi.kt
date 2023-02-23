package com.ars.data.remote.api

import com.ars.domain.model.Category
import com.ars.domain.model.ProductDetails
import retrofit2.http.GET

interface CategoryApi {
    @GET("/categories")
    suspend fun retrieveAllCategories(): List<Category>
}