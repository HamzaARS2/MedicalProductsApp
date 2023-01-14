package com.ars.data.extensions

import com.ars.data.model.ProductResponse
import com.ars.domain.model.Product

fun ProductResponse.toProduct(): Product =
    Product(
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