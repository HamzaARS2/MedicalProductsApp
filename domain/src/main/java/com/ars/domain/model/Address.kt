package com.ars.domain.model

data class Address(
    val id: Int,
    val streetAddress: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val streetName: String?,
    val number: Int?,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun getStreetInfo(): String? {
        return if (streetName != null && number != null)
            "$streetName, $number"
        else null
    }
}
