package com.ars.domain.model

import java.io.Serializable
import java.math.BigDecimal

data class OrderItem(
    val productId: Int,
    val productName: String,
    val productImage: String,
    val productUnitPrice: String,
    val quantity: Int,
    val subTotalPrice: BigDecimal
): Serializable

