package com.ars.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ars.domain.model.Product

@Entity("cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val customerId: String,
    val productId: Int,
    val quantity: Int = 1,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
