package com.ars.data.network.api

import com.ars.data.network.model.NetworkOrder
import com.ars.data.network.model.NetworkOrderRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {

    @POST("orders")
    suspend fun createOrder(@Body networkOrderRequest: NetworkOrderRequest): NetworkOrder

    @GET("orders/customer/{customerId}")
    suspend fun getCustomerOrders(@Path("customerId") customerId: String): List<NetworkOrder>

}