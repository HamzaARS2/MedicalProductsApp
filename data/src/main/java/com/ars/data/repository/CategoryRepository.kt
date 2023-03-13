package com.ars.data.repository

import com.ars.data.network.api.CategoryApi
import com.ars.domain.model.Category
import com.ars.domain.repository.product.IFetchRepository
import com.ars.domain.utils.Resource
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryApi: CategoryApi
): IFetchRepository<Category,Int> {

    override suspend fun retrieve(id: Int): Resource<Category> {
        return try {
            Resource.Success(categoryApi.retrieveCategory(id))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieveAll(): Resource<List<Category>?> {
        return try {
            Resource.Success(categoryApi.retrieveAllCategories())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}