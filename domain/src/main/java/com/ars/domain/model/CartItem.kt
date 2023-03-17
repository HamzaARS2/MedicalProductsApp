package com.ars.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class CartItem(
    val id: Int? = null,
    val customerId: String,
    val productId: Int,
    var quantity: Int,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val product: Product? = null,
)
