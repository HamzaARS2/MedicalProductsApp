package com.ars.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_products")
data class FavoriteProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val customerId: String,
    val productId: Int,
    val createdAt: Long
)
