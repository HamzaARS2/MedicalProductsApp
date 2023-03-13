package com.ars.data.network

import com.ars.data.extensions.toOnSaleProduct
import com.ars.data.network.api.ProductApi
import com.ars.data.network.network_model.NetworkProduct
import com.ars.domain.model.OnSaleProduct
import com.ars.domain.model.Product
import com.ars.domain.model.ProductDetails
import javax.inject.Inject

class ProductDataSource @Inject constructor(
    private val productApi: ProductApi
) {

    suspend fun fetchProductDetailsById(id: Int): ProductDetails =
        productApi.retrieveProductDetails(id)

    suspend fun fetchShopProducts(): List<NetworkProduct> =
        productApi.retrieveShopProducts()

    suspend fun fetchExclusiveProducts(): List<Product> =
        productApi.retrieveExclusiveProducts()

//    suspend fun fetchProducts(): List<Product> {
//        val exclusives = productApi.retrieveExclusiveProducts().map { it.asEntityProduct(isExclusive = true) }
//        val mostRated = productApi.retrieveMostRatedProducts().map { it.asEntityProduct(isExclusive = false) }
//        val onSale = productApi.retrieveOnSaleProducts().map {
//
//        }
//
//    }
    suspend fun fetchOnSaleProducts(): List<OnSaleProduct> =
        productApi.retrieveOnSaleProducts().map { it.toOnSaleProduct() }

    suspend fun fetchMostRatedProducts(): List<Product> =
        productApi.retrieveMostRatedProducts()

    suspend fun fetchProductsContaining(query: String): List<Product> =
        productApi.searchProducts(query)


}