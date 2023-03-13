package com.ars.domain.repository

import com.ars.domain.model.Category
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    suspend fun retrieve(id: Int): Resource<Category>
    fun fetchCategories(): Flow<Response<List<Category>>>
}