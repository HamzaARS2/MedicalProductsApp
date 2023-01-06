package com.reddevx.groceriesapp.data.network.repository

import com.reddevx.groceriesapp.api.CategoryService
import com.reddevx.groceriesapp.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val service: CategoryService
) : ICRUDRepository<Category,Int> {
    override suspend fun insert(data: Category): Category {
        return service.insertCategory(data)
    }

    override suspend fun retrieve(id: Int): Category? {
        return service.retrieveCategory(id)
    }

    override suspend fun retrieveAll(): List<Category> {
        return service.retrieveAllCategories()
    }

    override suspend fun update(id: Int, data: Category): Category {
        return service.updateCategory(id,data)
    }

    override suspend fun delete(id: Int): String {
        return service.deleteCategory(id)
    }
}