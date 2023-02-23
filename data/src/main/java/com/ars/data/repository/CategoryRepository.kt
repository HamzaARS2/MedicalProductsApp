package com.ars.data.repository

import com.ars.data.remote.api.CategoryApi
import com.ars.domain.model.Category
import com.ars.domain.utils.Resource
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryApi: CategoryApi
) {


    suspend fun fetchCategories(): Resource<List<Category>> {
        return try {
            Resource.Success(categoryApi.retrieveAllCategories())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}