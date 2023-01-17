package com.ars.data.remote.api

import com.ars.domain.model.Customer
import retrofit2.http.*

interface CustomerApi {

    @POST("customer/add")
    suspend fun insertCustomer(@Body customer: Customer): Customer

    @GET("/customer/{docId}")
    suspend fun retrieveCustomer(@Path("docId") docId: String): Customer?

    @GET("/customers")
    suspend fun retrieveAllCustomers(): List<Customer>

    @PUT("/customer/update")
    suspend fun updateCustomer(customer: Customer): Customer

    @DELETE("/customer/delete/{docId}")
    suspend fun deleteCustomer(@Path("docId") docId: String): String
}