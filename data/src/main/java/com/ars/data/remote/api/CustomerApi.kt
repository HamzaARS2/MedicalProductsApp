package com.ars.data.remote.api

import com.ars.domain.model.Customer
import retrofit2.http.*

interface CustomerApi {

    @POST("/customer/create")
    suspend fun insertCustomer(@Body customer: Customer): Customer

    @GET("/customer/doc/{docId}")
    suspend fun retrieveCustomer(@Path("docId") docId: String): Customer?

    @GET("/customer/all")
    suspend fun retrieveAllCustomers(): List<Customer>

    @PUT("/customer/update")
    suspend fun updateCustomer(@Body customer: Customer): Customer

    @DELETE("/customer/delete/{docId}")
    suspend fun deleteCustomer(@Path("docId") docId: String): String
}