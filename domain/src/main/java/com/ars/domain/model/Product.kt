package com.ars.domain.model

import java.io.Serializable
import java.math.BigDecimal

data class Product(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val image: String,
    val price: BigDecimal,
    val priceUnit: String,
    val rating: Float,
    val exclusive: Boolean,
    val createdAt: Long,
    val discount: Discount? = null
): Serializable