package com.ars.data.remote

import com.ars.data.extensions.toProduct
import com.ars.data.remote.api.ProductApi
import com.ars.domain.model.Product
import javax.inject.Inject

class ProductDataSource @Inject constructor(
    private val productApi: ProductApi
) {

    suspend fun fetchAllProducts(): List<Product> =
        productApi.retrieveAllProducts().map { it.toProduct() }

    suspend fun fetchExclusiveProducts(): List<Product> =
        productApi.retrieveExclusiveProducts().map { it.toProduct() }

}