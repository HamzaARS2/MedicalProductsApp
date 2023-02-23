package com.ars.data.remote

import com.ars.data.dto.OnSaleProductDTO
import com.ars.data.dto.toOnSaleProduct
import com.ars.data.extensions.toProduct
import com.ars.data.remote.api.ProductApi
import com.ars.domain.model.OnSaleProduct
import com.ars.domain.model.Product
import com.ars.domain.model.ProductDetails
import javax.inject.Inject

class ProductDataSource @Inject constructor(
    private val productApi: ProductApi
) {

    suspend fun fetchProductDetailsById(id: Int): ProductDetails =
        productApi.retrieveProductDetails(id)
    suspend fun fetchAllProducts(): List<ProductDetails> =
        productApi.retrieveAllProducts()

    suspend fun fetchExclusiveProducts(): List<Product> =
        productApi.retrieveExclusiveProducts()

    suspend fun fetchOnSaleProducts(): List<OnSaleProduct> =
        productApi.retrieveOnSaleProducts().map { it.toOnSaleProduct() }
    suspend fun fetchMostRatedProducts(): List<Product> =
        productApi.retrieveMostRatedProducts()



}