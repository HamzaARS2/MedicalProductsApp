package com.ars.data.extensions

import com.ars.data.dto.ProductDTO
import com.ars.domain.model.ProductDetails

fun ProductDTO.toProduct(): ProductDetails =
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

