package com.ars.data.extensions

import com.ars.data.dto.OnSaleProductDto
import com.ars.data.dto.ProductDto
import com.ars.data.local.entity.CategoryEntity
import com.ars.data.local.entity.DiscountEntity
import com.ars.data.local.entity.ProductEntity
import com.ars.data.local.relation.DiscountAndProduct
import com.ars.data.model.NetworkDiscount
import com.ars.data.network.network_model.NetworkCategory
import com.ars.data.network.network_model.NetworkProduct
import com.ars.data.network.network_model.NetworkReview
import com.ars.domain.model.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

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

fun NetworkReview.asReview() =
    Review(
        id = this.id,
        productId = this.productId,
        customer = this.customer,
        comment = this.comment,
        rating = this.rating,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

fun NetworkProduct.asProductEntity() =
    ProductEntity(
        productId = id,
        categoryId = categoryId,
        name = name,
        description = description,
        image = image,
        price = price,
        priceUnit = priceUnit,
        rating = rating,
        exclusive = exclusive,
        productDiscountId = discountId
    )

fun ProductEntity.asProduct() =
    Product(
        id = productId,
        categoryId = categoryId,
        name = name,
        price = BigDecimal(price.toDouble()).setScale(2, RoundingMode.HALF_UP),
        priceUnit = priceUnit,
        image = image,
        rating = rating,
        exclusive = exclusive
    )

fun DiscountAndProduct.asProduct(): Product {
    val productEntity = this.productEntity
    val discountEntity = this.discountEntity
    return Product(
        id = productEntity.productId,
        categoryId = productEntity.categoryId,
        name = productEntity.name,
        price = BigDecimal(productEntity.price.toDouble()).setScale(2, RoundingMode.HALF_UP),
        priceUnit = productEntity.priceUnit,
        image = productEntity.image,
        rating = productEntity.rating,
        exclusive = productEntity.exclusive,
        discount = discountEntity?.asDiscount()
    )
}

fun DiscountEntity.asDiscount() =
    Discount(
        id = discountId,
        percentage = discountPercentage,
        startDate = startDate,
        endDate = endDate
    )


fun NetworkDiscount.asDiscountEntity() =
    DiscountEntity(
        discountId = id,
        discountPercentage = discountPercentage,
        salePrice = salePrice,
        startDate = convertDateToLong(startDate),
        endDate = convertDateToLong(endDate)
    )

fun CategoryEntity.asCategory() =
    Category(
        id = id,
        name = name,
        image = image,
        color = color.substring(0, 1) + "1A" + color.substring(1, color.length),
        strokeColor = color.substring(0, 1) + "4A" + color.substring(1, color.length)
    )

fun NetworkCategory.asCategoryEntity() =
    CategoryEntity(
        id = id,
        name = name,
        image = image,
        color = color
    )


fun convertDateToLong(stringDate: String): Long {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(stringDate)?.time
        ?: Date().time
}

