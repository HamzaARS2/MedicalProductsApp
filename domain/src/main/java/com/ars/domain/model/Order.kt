package com.ars.domain.model

import java.math.BigDecimal
import java.sql.Timestamp

data class Order(
    val id: Int? = null,
    val customerId: Int = 0,
    val status: String = "Pending",
    val totalPrice: BigDecimal = BigDecimal("0.00"),
    val timestamp: Timestamp? = null,
    val orderItems: List<OrderItem> = listOf()
)

