package com.ars.data.repository

import androidx.room.withTransaction
import com.ars.data.extensions.asCategory
import com.ars.data.extensions.asCategoryEntity
import com.ars.data.local.GroceriesDatabase
import com.ars.data.network.api.CategoryApi
import com.ars.data.util.networkBoundResource
import com.ars.domain.model.Category
import com.ars.domain.repository.ICategoryRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryApi: CategoryApi,
    private val database: GroceriesDatabase
): ICategoryRepository {

    private val categoryDao = database.getCategoryDao()
    override suspend fun retrieve(id: Int): Resource<Category> {
        return try {
            Resource.Success(categoryApi.retrieveCategory(id))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun fetchCategories(): Flow<Response<List<Category>>> = networkBoundResource(
        query = {
            categoryDao.retrieveCategories().map { categoryList ->
                categoryList.map { it.asCategory() }
            }
        },

        fetch = {
            categoryApi.retrieveAllCategories()
        },

        saveFetchResult = { networkCategories ->
            database.withTransaction {
                categoryDao.deleteCategories()
                categoryDao.insertCategories(networkCategories.map { it.asCategoryEntity() })
            }
        }
    )
}