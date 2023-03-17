package com.ars.data.network.model

import com.ars.domain.model.Customer

data class NetworkReview(
    val id: Int,
    val productId: Int,
    val customerId: String,
    val comment: String,
    val rating: Float,
    val createdAt: String,
    val updatedAt: String,
    val customer: Customer
)
