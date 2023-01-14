package com.ars.groceriesapp.api

import com.ars.groceriesapp.model.Category
import retrofit2.http.*

interface CategoryService {

    @GET("/categories/{id}")
    suspend fun retrieveCategory(@Path("id") id: Int): Category

    @GET("/categories")
    suspend fun retrieveAllCategories():List<Category>

}
