package com.ars.domain.model

data class Address(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val phone: String?,
    val streetAddress: String,
    val city: String,
    val state: String? = null,
    val country: String = "Morocco",
    val createdAt: Long? = null,
    val updatedAt: Long? = null
) {

    fun getFullAddress() =
        "$firstName $lastName\n$phone\n$streetAddress, $city \n$country"
}
