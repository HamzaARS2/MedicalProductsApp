package com.ars.domain.model

import java.util.*

data class Discount(
    val id: Int,
    val percentage: Int,
    val startDate: Long,
    val endDate: Long
) {
    fun isOnSale() = endDate > Date().time

}