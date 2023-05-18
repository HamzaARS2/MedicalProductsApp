package com.ars.domain.repository.product

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.model.ProductDetails
import com.ars.domain.model.ProductReview
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    fun fetchProductDetails(customerId: String?, productId: Int): Flow<Response<ProductDetails>>
    fun fetchShopProducts(): Flow<Response<List<Product>?>>
    fun searchProducts(query: String, categoryId: Int): Flow<Response<List<Product>>>

    fun getOrderProducts(orderId: Int): Flow<Response<List<ProductReview>>>
}