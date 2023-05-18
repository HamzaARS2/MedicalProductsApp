package com.ars.domain.model

data class ReviewRequest(
    val productId: Int,
    val customerId: String,
    val rating: Float,
    val comment: String,
)