package com.ars.domain.model

import java.math.BigDecimal

data class Order(
    val id: Int,
    val customerId: String,
    val status: String,
    val trackNumber: String,
    val totalPrice: BigDecimal,
    val paymentMethod: String,
    val createdAt: String
)
