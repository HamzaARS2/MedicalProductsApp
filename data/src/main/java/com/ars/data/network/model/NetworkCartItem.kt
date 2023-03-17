package com.ars.data.network.model

import com.ars.domain.model.Product

data class NetworkCartItem(
    val id: Int,
    val customerId: String,
    val productId: Int,
    var quantity: Int,
    val createdAt: String?,
    val updatedAt: String?,
    val product: NetworkProduct?,
)