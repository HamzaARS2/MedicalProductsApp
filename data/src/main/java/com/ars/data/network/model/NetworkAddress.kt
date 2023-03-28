package com.ars.data.network.model


data class NetworkAddress(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val phone: String?,
    val streetAddress: String,
    val city: String,
    val state: String? = null,
    val country: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
