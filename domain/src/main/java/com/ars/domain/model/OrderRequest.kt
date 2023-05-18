package com.ars.domain.model

import java.io.Serializable
import java.math.BigDecimal

data class OrderRequest(
    val customerId: String,
    val addressId: Int,
    val totalPrice: BigDecimal,
    val orderItems: List<OrderItem> = listOf()
): Serializable

