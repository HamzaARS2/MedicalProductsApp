package com.ars.data.model

import com.ars.domain.model.Product
import java.math.BigDecimal
import java.util.Date

data class OnSaleProductResponse(
    val id: Int,
    val productId: Int,
    val discount: BigDecimal,
    val salePrice: BigDecimal,
    val startDate: Date,
    val endDate: Date,
    val product: Product
)