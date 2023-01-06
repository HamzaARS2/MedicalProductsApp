package com.reddevx.groceriesapp.api

import com.reddevx.groceriesapp.model.OrderItem
import retrofit2.http.*

interface OrderItemService {

    @POST("/orderItems/add")
    suspend fun insertOrderItem(@Body orderItem: OrderItem): OrderItem

    @GET("/orderItems/{id}")
    suspend fun retrieveOrderItem(@Path("id") id: Int): OrderItem

}