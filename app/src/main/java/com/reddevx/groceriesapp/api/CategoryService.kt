package com.reddevx.groceriesapp.api

import com.reddevx.groceriesapp.model.Category
import com.reddevx.groceriesapp.model.Product
import retrofit2.http.*

interface CategoryService {

    @POST("/categories/add")
    suspend fun insertCategory(@Body category: Category): Category

    @GET("/categories/{id}")
    suspend fun retrieveCategory(@Path("id") id: Int): Category

    @GET("/categories")
    suspend fun retrieveAllCategories(): List<Category>

    @PUT("/categories/update/{id}")
    suspend fun updateCategory(@Path("id") id: Int, @Body category: Category): Category

    @DELETE("/categories/delete/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): String

}
