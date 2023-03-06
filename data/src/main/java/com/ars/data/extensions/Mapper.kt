package com.ars.data.extensions

import com.ars.data.dto.OnSaleProductDto
import com.ars.data.dto.ProductDto
import com.ars.domain.model.OnSaleProduct
import com.ars.domain.model.ProductDetails

fun ProductDto.toProduct(): ProductDetails =
    ProductDetails(
        id = id,
        categoryId = categoryId,
        name = name,
        description = description,
        price = price,
        priceUnit = priceUnit,
        nutrition = nutrition,
        image = image,
        isExclusive = isExclusive
    )

fun OnSaleProductDto.toOnSaleProduct() =
    OnSaleProduct(
        id = this.id,
        productId = this.productId,
        discountPercentage = this.discount.toDouble(),
        startDate = this.startDate.toString(),
        endDate = this.endDate.toString(),
        salePrice = this.salePrice.toDouble(),
        product = this.product
    )

