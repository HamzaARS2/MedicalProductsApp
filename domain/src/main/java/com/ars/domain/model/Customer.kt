package com.ars.domain.model

import java.io.Serializable

data class Customer(
    val id: String,
    val name: String,
    val email: String,
    var phone: String?,
    var address: Address? = null,
    var location: Location? = null
): Serializable {

}
