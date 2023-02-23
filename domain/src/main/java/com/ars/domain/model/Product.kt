package com.ars.domain.model

import java.math.BigDecimal

data class Product(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val price: BigDecimal,
    val priceUnit: String,
    val image: String
)