package com.ars.domain.repository.product

import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.model.ProductDetails
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    fun fetchProductDetails(customerId: String, productId: Int): Flow<Response<ProductDetails>>
    fun fetchShopProducts(): Flow<Response<List<Product>?>>
    suspend fun searchProducts(query: String): Resource<List<Product>>
}