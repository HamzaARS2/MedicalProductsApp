package com.ars.data.network.model

import com.ars.domain.model.Address
import com.ars.domain.model.Location

data class NetworkCustomer(
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val addressId: Int?,
    val locationId: Int?,
    val address: NetworkAddress? = null,
    val location: Location? = null
)
