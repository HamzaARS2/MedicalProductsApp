package com.ars.domain.model

import java.math.BigDecimal

data class ProductDetails(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    val price: BigDecimal,
    val priceUnit: String,
    val rating: Float,
    val isExclusive: Boolean,
    val isFavorite: Boolean,
    val discount: Discount?,
    val reviews: List<Review>?,
    val similarProducts: List<Product>?
)