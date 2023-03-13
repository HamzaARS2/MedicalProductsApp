package com.ars.domain.repository.product

import com.ars.domain.model.OnSaleProduct
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.model.ProductDetails
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun retrieve(id: Int): Resource<ProductDetails>
    fun fetchShopProducts(): Flow<Response<List<Product>?>>
    suspend fun searchProducts(query: String): Resource<List<Product>>
}