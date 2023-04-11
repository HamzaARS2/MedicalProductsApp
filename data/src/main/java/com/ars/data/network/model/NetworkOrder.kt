package com.ars.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.sql.Timestamp

data class NetworkOrder(
    val id: Int,
    val customerId: String,
    val status: String,
    val trackNumber: String,
    val estimatedDeliveryDate: String,
    val totalPrice: BigDecimal,
    @SerializedName("paymentInfo")
    val paymentMethod: String,
    val timestamp: Timestamp,
    val orderItems: List<NetworkOrderItem>
)
