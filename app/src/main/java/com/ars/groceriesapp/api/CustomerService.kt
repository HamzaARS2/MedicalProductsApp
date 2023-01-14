package com.ars.groceriesapp.api

import com.ars.groceriesapp.model.Customer
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface CustomerService {
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