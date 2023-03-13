package com.ars.data.model

import java.util.Date

data class NetworkDiscount(
    val id: Int,
    val discountPercentage: Int,
    val salePrice: Double,
    val startDate: String,
    val endDate: String
)