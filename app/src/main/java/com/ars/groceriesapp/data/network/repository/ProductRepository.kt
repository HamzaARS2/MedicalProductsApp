package com.ars.groceriesapp.data.network.repository

import com.ars.domain.Resource
import com.ars.groceriesapp.api.ProductService
import com.ars.groceriesapp.data.network.repository.auth.IProductsProvider
import com.ars.groceriesapp.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val service: ProductService
) : IProductsProvider {

    override suspend fun retrieve(id: Int): Resource<Product?> {
        return try {
            val response = service.retrieveProduct(id)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieveAll(): Resource<List<Product>?> {
        return try {
            val response = service.retrieveAllProducts()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieveExclusive(): Resource<List<Product>?> {
        return try {
            val response = service.retrieveExclusiveProducts()
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