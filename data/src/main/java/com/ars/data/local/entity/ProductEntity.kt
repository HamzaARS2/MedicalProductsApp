package com.ars.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val productId: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    @ColumnInfo(name = "price_unit")
    val priceUnit: String,
    val rating: Float,
    val exclusive: Boolean,
    @ColumnInfo(name = "product_discount_id")
    val productDiscountId: Int? = null,
)