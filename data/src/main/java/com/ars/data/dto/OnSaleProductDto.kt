package com.ars.data.dto

import com.ars.domain.model.Product
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.Date

data class OnSaleProductDto(
    val id: Int,
    val productId: Int,
    @SerializedName("discountPercentage")
    val discount: BigDecimal,
    val salePrice: BigDecimal,
    val startDate: Date,
    val endDate: Date,
    val product: Product
)


