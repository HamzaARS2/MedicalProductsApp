package com.ars.domain.model

data class FavoriteProduct(
    val id: Int? = null,
    val customerId: String,
    val productId: Int,
    val createdAt: String? = null,
    val product: Product? = null
)
