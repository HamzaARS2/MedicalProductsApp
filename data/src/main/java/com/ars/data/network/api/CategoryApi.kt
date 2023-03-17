package com.ars.data.network.api

import com.ars.data.network.model.NetworkCategory
import com.ars.domain.model.Category
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("categories")
    suspend fun retrieveAllCategories(): List<NetworkCategory>

    @GET("categories/{id}")
    suspend fun retrieveCategory(@Path("id") id: Int): Category
}