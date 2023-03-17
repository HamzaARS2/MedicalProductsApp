package com.ars.data.network.model

data class NetworkFavoriteProduct(
    val id: Int,
    val customerId: String,
    val productId: Int,
    val createdAt: String,
    val product: NetworkProduct
)
