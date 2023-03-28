package com.ars.data.network.api

import com.ars.data.network.model.NetworkAddress
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApi {

    @POST("address/{customerId}")
    suspend fun createAddress(@Body address: NetworkAddress, @Path("customerId") customerId: String): NetworkAddress

    @PUT("address")
    suspend fun updateAddress(@Body address: NetworkAddress): NetworkAddress
}