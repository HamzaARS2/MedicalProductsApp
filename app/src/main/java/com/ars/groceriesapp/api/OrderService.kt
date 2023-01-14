package com.ars.groceriesapp.api

import com.ars.groceriesapp.model.Order
import retrofit2.http.*

interface OrderService {

    @POST("/orders/add")
    suspend fun insertOrder(@Body order: Order): Order

    @GET("/orders/customer/{id}")
    suspend fun retrieveCustomerOrders(@Path("id") id: Int): List<Order>

}