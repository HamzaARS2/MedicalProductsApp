package com.ars.data.model

import java.math.BigDecimal

data class ProductResponse(
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
