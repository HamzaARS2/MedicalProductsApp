package com.ars.domain.model

data class FavoriteProduct(
    val id: Int = 0,
    val customerId: String,
    val createdAt: Long,
    val product: Product
)
