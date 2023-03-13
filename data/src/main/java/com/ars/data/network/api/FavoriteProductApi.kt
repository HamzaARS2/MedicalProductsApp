package com.ars.data.network.api

import com.ars.domain.model.FavoriteProduct
import retrofit2.http.*

interface FavoriteProductApi {

    @GET("products/favorites/{id}")
    suspend fun getCustomerFavoriteProducts(@Path("id") id: String): List<FavoriteProduct>

    @POST("products/favorites/create")
    suspend fun addProductToCustomerFavorites(@Body favoriteProduct: FavoriteProduct)

    @DELETE("products/favorites/delete/{id}")
    suspend fun deleteCustomerFavoriteProduct(@Path("id") id: Int)

    @DELETE("products/favorites/delete/{productId}/{customerId}")
    suspend fun deleteCustomerFavoriteProduct(@Path("customerId") customerId: String, @Path("productId") productId: Int)


}