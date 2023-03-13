package com.ars.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("discount")
data class DiscountEntity(
    @PrimaryKey(autoGenerate = false)
    val discountId: Int,
    val discountPercentage: Int,
    val salePrice: Double,
    val startDate: Long,
    val endDate: Long
)
