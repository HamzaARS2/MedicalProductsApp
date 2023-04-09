package com.ars.data.network.api

import com.ars.data.network.model.NetworkOrder
import com.ars.data.network.model.NetworkOrderDetails
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {

    @POST("orders")
    suspend fun createOrder(@Body networkOrder: NetworkOrder): NetworkOrderDetails

    @GET("orders/{customerId}")
    suspend fun getCustomerOrders(@Path("customerId") customerId: String): List<NetworkOrderDetails>

}