package com.ars.data.network.api

import com.ars.data.network.model.NetworkCustomer
import com.ars.domain.model.Customer
import retrofit2.http.*

interface CustomerApi {

    @POST("customers")
    suspend fun insertCustomer(@Body networkCustomer: NetworkCustomer): NetworkCustomer

    @GET("customers/{id}")
    suspend fun retrieveCustomer(@Path("id") id: String): NetworkCustomer?

    @GET("customers")
    suspend fun retrieveAllCustomers(): List<NetworkCustomer>

    @PUT("customers/update")
    suspend fun updateCustomer(@Body customer: Customer): NetworkCustomer

    @DELETE("customer/delete/{id}")
    suspend fun deleteCustomer(@Path("id") id: String): String
}