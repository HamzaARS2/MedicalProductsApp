package com.ars.data.network.model

import java.math.BigDecimal

data class NetworkOrderRequest(
    val customerId: String,
    val addressId: Int,
    val totalPrice: BigDecimal,
    val orderItems: List<NetworkOrderItem>
)
