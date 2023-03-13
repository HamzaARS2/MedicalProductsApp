package com.ars.data.network.api

import com.ars.data.dto.OnSaleProductDto
import com.ars.data.network.network_model.NetworkProduct
import com.ars.domain.model.Product
import com.ars.domain.model.ProductDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products/{id}")
    suspend fun retrieveProductDetails(@Path("id") id: Int): ProductDetails

    @GET("products/shop")
    suspend fun retrieveShopProducts(): List<NetworkProduct>

    @GET("products/exclusive")
    suspend fun retrieveExclusiveProducts(): List<Product>

    @GET("products/most_rated")
    suspend fun retrieveMostRatedProducts(): List<Product>

    @GET("onsaleproducts")
    suspend fun retrieveOnSaleProducts(): List<OnSaleProductDto>

    @GET("products/search")
    suspend fun searchProducts(@Query("query") query: String): List<Product>
}