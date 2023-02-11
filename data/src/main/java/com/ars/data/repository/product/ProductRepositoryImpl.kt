package com.ars.data.repository.product

import com.ars.data.remote.ProductDataSource
import com.ars.domain.utils.Resource
import com.ars.domain.model.Product
import com.ars.domain.repository.product.IProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource
): IProductRepository {


    override suspend fun retrieve(id: Int): Resource<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun retrieveAll(): Resource<List<Product>?> {
        return try {
            val response = productDataSource.fetchAllProducts()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieveExclusive(): Resource<List<Product>?> {
        return try {
            val response = productDataSource.fetchExclusiveProducts()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieveMostRated(): Resource<List<Product>?> {
        TODO("Not yet implemented")
    }
}