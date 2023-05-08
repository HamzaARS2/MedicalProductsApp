package com.ars.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Order(
    val id: Int,
    val customerId: String,
    val status: String,
    val trackNumber: String,
    val totalPrice: BigDecimal,
    val paymentMethod: String,
    val createdAt: String
): Parcelable
