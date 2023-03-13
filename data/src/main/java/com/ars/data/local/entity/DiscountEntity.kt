package com.ars.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("discount")
data class DiscountEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "discount_id")
    val discountId: Int,
    val discountPercentage: Int,
    val salePrice: Double,
    val startDate: Long,
    val endDate: Long
)
