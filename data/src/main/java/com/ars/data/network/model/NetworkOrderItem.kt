package com.ars.data.network.model

import java.math.BigDecimal

data class NetworkOrderItem(
    val productId: Int,
    val quantity: Int,
    val subTotalPrice: BigDecimal
)
