package com.ars.data.repository

import android.util.Log
import com.ars.data.extensions.asNetworkOrder
import com.ars.data.network.api.OrderApi
import com.ars.domain.model.Order
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderApi: OrderApi
) {


    fun getOrders(customerId: String) = flow {
        emit(Response.Loading())
        val result = try {
            val response = orderApi.getCustomerOrders(customerId)
            Response.Success(response)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable)
        }

        emit(result)
    }


    fun placeNewOrder(order: Order) = flow {
        emit(Response.Loading())
        val result = try {
            val response = orderApi.createOrder(order.asNetworkOrder())
            Log.d("placeNewOrder", "placeNewOrder: response = $response")
            Response.Success(response)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable)
        }
        emit(result)
    }

}