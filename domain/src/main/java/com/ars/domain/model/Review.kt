package com.ars.domain.model

data class Review(
    val id: Int,
    val productId: Int,
    val customer: Customer,
    val comment: String,
    val rating: Float,
    val createdAt: String,
    val updatedAt: String
)