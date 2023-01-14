package com.ars.domain.model

import java.math.BigDecimal

data class OrderItem(
    val id: Int? = null,
    val orderId: Int = 0,
    val productId: Int = 0,
    val quantity: Int = 1,
    val subTotalPrice: BigDecimal = BigDecimal("0.00")
)

