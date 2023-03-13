package com.ars.domain.model

data class Discount(
    val id: Int,
    val percentage: Int,
    val startDate: Long,
    val endDate: Long
)