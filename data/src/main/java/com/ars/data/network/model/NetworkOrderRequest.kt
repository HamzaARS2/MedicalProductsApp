package com.ars.data.network.model

import java.math.BigDecimal

data class NetworkOrderRequest(
    val customerId: String,
    val totalPrice: BigDecimal,
    val orderItems: List<NetworkOrderItem>
)
