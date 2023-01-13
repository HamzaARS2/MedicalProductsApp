package com.reddevx.groceriesapp.data.network.repository

import com.reddevx.groceriesapp.api.ProductService
import com.reddevx.groceriesapp.data.network.Resource
import com.reddevx.groceriesapp.data.network.repository.auth.IProductsProvider
import com.reddevx.groceriesapp.model.Order
import com.reddevx.groceriesapp.model.Product
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