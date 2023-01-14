package com.ars.domain.model

data class Customer(
    val id: Int? = null,
    val docId: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = ""
)
