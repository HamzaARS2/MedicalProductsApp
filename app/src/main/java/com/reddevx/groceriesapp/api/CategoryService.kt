package com.reddevx.groceriesapp.api

import com.reddevx.groceriesapp.data.network.Resource
import com.reddevx.groceriesapp.model.Category
import com.reddevx.groceriesapp.model.Product
import retrofit2.http.*

interface CategoryService {

    @GET("/categories/{id}")
    suspend fun retrieveCategory(@Path("id") id: Int): Category

    @GET("/categories")
    suspend fun retrieveAllCategories():List<Category>

}
