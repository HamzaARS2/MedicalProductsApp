package com.ars.domain.model

import java.math.BigDecimal
import java.util.Date

data class OnSaleProduct(
    val id: Int,
    val productId: Int,
    val discountPercentage: Double,
    val startDate: String,
    val endDate: String,
    val salePrice: Double,
    val product: Product
)