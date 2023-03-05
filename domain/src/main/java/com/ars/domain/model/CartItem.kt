package com.ars.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class CartItem(
    val id: Int,
    val customerId: String,
    val product: Product,
    var quantity: Int,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
