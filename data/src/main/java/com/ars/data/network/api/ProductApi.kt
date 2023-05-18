package com.ars.data.network.api

import com.ars.data.network.model.NetworkProduct
import com.ars.data.network.model.NetworkProductDetails
import com.ars.domain.model.ProductReview
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products/details/{id}")
    suspend fun retrieveProductDetails(@Path("id") id: Int): NetworkProductDetails

    @GET("products/shop")
    suspend fun retrieveShopProducts(): List<NetworkProduct>

    @GET("products/search")
    suspend fun searchProducts(
        @Query("query") query: String,
        @Query("categoryId") categoryId: Int
    ): List<NetworkProduct>

    @GET("products/order/{orderId}")
    suspend fun fetchOrderProducts(@Path("orderId") orderId: Int): List<ProductReview>

}