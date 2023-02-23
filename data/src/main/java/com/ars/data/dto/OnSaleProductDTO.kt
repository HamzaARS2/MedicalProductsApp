package com.ars.data.dto

import com.ars.domain.model.OnSaleProduct
import com.ars.domain.model.Product
import com.ars.domain.model.ProductDetails
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.Date

data class OnSaleProductDTO(
    val id: Int,
    val productId: Int,
    @SerializedName("discountPercentage") val discount: BigDecimal,
    val salePrice: BigDecimal,
    val startDate: Date,
    val endDate: Date,
    @SerializedName("productDTO") val product: Product
)


fun OnSaleProductDTO.toOnSaleProduct() =
    OnSaleProduct(
        id = this.id,
        productId = this.productId,
        discountPercentage = this.discount.toDouble(),
        startDate = this.startDate.toString(),
        endDate = this.endDate.toString(),
        salePrice = this.salePrice.toDouble(),
        product = this.product
    )