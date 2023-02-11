package com.ars.domain.model

import java.io.Serializable

data class Customer(
    val id: Int? = null,
    val docId: String = "",
    val name: String = "",
    val email: String = "",
    var phone: String = "",
    var address: String = ""
): Serializable
