package com.ars.data.network.api

import com.ars.data.network.model.NetworkCartItem
import com.ars.domain.model.CartItem
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApi {

    @GET("cart_items/{id}")
    suspend fun getCustomerCartItems(@Path("id") id: String): List<NetworkCartItem>

    @POST("cart_items/create/{customerId}/{productId}")
    suspend fun addItemToCustomerCart(
        @Path("customerId") customerId: String,
        @Path("productId") productId: Int
    ): NetworkCartItem

    @POST("cart_items/create/multiple/{customerId}")
    suspend fun saveMultipleItemsToCustomerCart(@Path("customerId") customerId: String, @Body productIds: IntArray): List<NetworkCartItem>

    @DELETE("cart_items/delete/{id}")
    suspend fun deleteCustomerCartItem(
        @Path("id") id: Int
    )
}