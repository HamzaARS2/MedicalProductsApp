package com.ars.data.network.model

data class NetworkDiscount(
    val id: Int,
    val discountPercentage: Int,
    val salePrice: Double,
    val startDate: String,
    val endDate: String
)