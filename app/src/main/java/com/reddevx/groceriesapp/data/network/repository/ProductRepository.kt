package com.reddevx.groceriesapp.data.network.repository

import com.reddevx.groceriesapp.api.ProductService
import com.reddevx.groceriesapp.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val service: ProductService
): IRepository<Product,Int> {
    override suspend fun insert(data: Product): Product {
        return service.insertProduct(data)
    }

    override suspend fun retrieve(id: Int): Product? {
        return service.retrieveProduct(id)
    }

    override suspend fun retrieveAll(): List<Product> {
        return service.retrieveAllProducts()
    }

    override suspend fun update(id: Int, data: Product): Product {
        return service.updateProduct(id,data)
    }

    override suspend fun delete(id: Int): String {
        return service.deleteProduct(id)
    }




}