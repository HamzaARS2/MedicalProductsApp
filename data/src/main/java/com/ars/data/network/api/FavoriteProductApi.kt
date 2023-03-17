package com.ars.data.network.api

import com.ars.data.network.model.NetworkFavoriteProduct
import com.ars.domain.model.FavoriteProduct
import retrofit2.http.*

interface FavoriteProductApi {

    @GET("products/favorites/{id}")
    suspend fun fetchCustomerFavoriteProducts(@Path("id") id: String): List<NetworkFavoriteProduct>

    @POST("products/favorites/create/{productId}/{customerId}")
    suspend fun addProductToCustomerFavorites(@Path("productId") productId: Int, @Path("customerId") customerId: String): NetworkFavoriteProduct

    @DELETE("products/favorites/delete/{productId}/{customerId}")
    suspend fun deleteCustomerFavoriteProduct(@Path("customerId") customerId: String, @Path("productId") productId: Int)


}