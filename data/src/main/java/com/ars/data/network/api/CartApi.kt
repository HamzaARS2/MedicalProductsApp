package com.ars.data.network.api

import com.ars.domain.model.CartItem
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApi {

    @GET("cart_items/{id}")
    suspend fun getCustomerCartItems(@Path("id") id: String): List<CartItem>

    @POST("cart_items/create")
    suspend fun addItemToCustomerCart(@Body cartItem: CartItem): CartItem

    @POST("cart_items/multiple/create")
    suspend fun addMultipleItemsToCustomerCart(@Body cartItems: List<CartItem>)
    @DELETE("cart_items/delete/{id}")
    suspend fun deleteCustomerCartItem(@Path("id") id: Int)
}