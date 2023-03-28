package com.ars.domain.model

import java.io.Serializable
import java.math.BigDecimal
import java.sql.Timestamp

data class Order(
    val customerId: String,
    val totalPrice: BigDecimal,
    val orderItems: List<OrderItem> = listOf()
): Serializable

