package com.ars.data.dto

import java.math.BigDecimal

data class ProductDto(
    val id:Int,
    val categoryId:Int,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val priceUnit: String,
    val nutrition: String,
    val image: String,
    val isExclusive: Boolean
)
