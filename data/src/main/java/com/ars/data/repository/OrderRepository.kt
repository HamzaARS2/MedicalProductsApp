package com.ars.data.repository

import android.util.Log
import com.ars.data.extensions.asNetworkOrder
import com.ars.data.extensions.asOrder
import com.ars.data.network.api.OrderApi
import com.ars.domain.model.OrderRequest
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderApi: OrderApi
) {


    fun getOrders(customerId: String) = flow {
        emit(Response.Loading())
        val result = try {
            val response = orderApi.getCustomerOrders(customerId).map {
                it.asOrder()
            }
            Response.Success(response)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable)
        }

        emit(result)
    }


    fun placeNewOrder(orderRequest: OrderRequest) = flow {
        emit(Response.Loading())
        val result = try {
            val response = orderApi.createOrder(orderRequest.asNetworkOrder())
            Log.d("placeNewOrder", "placeNewOrder: response = $response")
            Response.Success(response)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable)
        }
        emit(result)
    }

}