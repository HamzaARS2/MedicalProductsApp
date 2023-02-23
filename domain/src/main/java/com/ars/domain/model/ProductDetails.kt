package com.ars.domain.model

import java.math.BigDecimal

data class ProductDetails(
    val id: Int? = null,
    val categoryId: Int? = null,
    val name: String = "",
    val description: String = "",
    val price: BigDecimal = BigDecimal("0.00"),
    val priceUnit: String = "",
    val nutrition: String? = null,
    var image: String? = null,
    val isExclusive: Boolean = false,
    val rating: Float = 0.0F
)