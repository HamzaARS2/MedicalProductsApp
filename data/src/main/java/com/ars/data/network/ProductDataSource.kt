package com.ars.data.network

import com.ars.data.extensions.asProduct
import com.ars.data.extensions.asProductDetails
import com.ars.data.network.api.ProductApi
import com.ars.data.network.model.NetworkProduct
import com.ars.data.network.model.NetworkProductDetails
import com.ars.domain.model.Product
import com.ars.domain.model.ProductDetails
import javax.inject.Inject

class ProductDataSource @Inject constructor(
    private val productApi: ProductApi
) {

    suspend fun fetchProductDetailsById(id: Int): NetworkProductDetails =
        productApi.retrieveProductDetails(id)

    suspend fun fetchShopProducts(): List<NetworkProduct> =
        productApi.retrieveShopProducts()

    suspend fun fetchProductsContaining(query: String, categoryId: Int): List<Product> =
        productApi.searchProducts(query, categoryId).map { it.asProduct() }

    suspend fun fetchOrderProductsById(orderId: Int) =
        productApi.fetchOrderProducts(orderId)

}