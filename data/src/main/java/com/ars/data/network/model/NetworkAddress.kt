package com.ars.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkAddress(
    val id: Int,
    val addressLine: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val streetName: String?,
    val number: Int?,
    val createdAt: String,
    val updatedAt: String
){
    fun getStreetInfo() = "$streetName, $number"
}
