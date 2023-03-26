package com.ars.data.extensions

import com.ars.data.local.entity.*
import com.ars.data.local.relations.CartAndProduct
import com.ars.data.local.relations.DiscountAndProduct
import com.ars.data.local.relations.FavoriteAndProduct
import com.ars.data.network.model.*
import com.ars.domain.model.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*




fun NetworkCustomer.asCustomer() =
    Customer(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address?.asAddress(),
        location = location
    )

fun Customer.asNetworkCustomer() =
    NetworkCustomer(
        id = id,
        name = name,
        email = email,
        phone = phone,
        addressId = address?.id,
        locationId = location?.id
    )

fun NetworkAddress.asAddress() =
    Address(
        id = id,
        streetAddress = addressLine,
        city = city,
        state = state,
        country = country,
        streetName =  streetName,
        number = number,
        createdAt = convertDateToLong(createdAt),
        updatedAt = convertDateToLong(updatedAt)
    )
fun NetworkReview.asReview() =
    Review(
        id = this.id,
        productId = this.productId,
        customer = this.networkCustomer.asCustomer(),
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
        productDiscountId = discountId,
        createdAt = convertDateToLong(createdAt)
    )

fun NetworkProduct.asProduct() =
    Product(
        id = id,
        categoryId = categoryId,
        name = name,
        image = image,
        price = BigDecimal(price.toDouble()).setScale(2, RoundingMode.HALF_UP),
        priceUnit = priceUnit,
        rating = rating,
        exclusive = exclusive,
        discount = networkDiscount?.asDiscount(),
        createdAt = convertDateToLong(createdAt)
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
        exclusive = exclusive,
        createdAt = createdAt
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
        discount = discountEntity?.asDiscount(),
        createdAt = productEntity.createdAt
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

fun NetworkDiscount.asDiscount() =
    Discount(
        id = id,
        percentage = discountPercentage,
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

fun NetworkProductDetails.asProductDetails(isFavorite: Boolean = false) =
    ProductDetails(
        id = id,
        categoryId = categoryId,
        name = name,
        description = description,
        image = image,
        price = BigDecimal(price.toDouble()).setScale(2, RoundingMode.HALF_UP),
        priceUnit = priceUnit,
        rating = rating,
        isExclusive = exclusive,
        isFavorite = isFavorite,
        discount = networkDiscount?.asDiscount(),
        reviews = reviews?.map { it.asReview() },
        similarProducts = similarProducts?.map { it.asProduct() }
    )


fun NetworkCartItem.asCartItemEntity() =
    CartItemEntity(
        id = id,
        customerId = customerId,
        productId = productId,
        createdAt = convertDateToLong(createdAt),
        updatedAt = convertDateToLong(updatedAt)
    )

fun CartItem.asCartItemEntity() =
    CartItemEntity(
        id = id!!,
        customerId = customerId,
        productId = productId,
        quantity = quantity,
    )

fun CartAndProduct.asCartItem(): CartItem {
    val cartItemEntity = this.cartItemEntity
    val productEntity = this.productEntity
    return CartItem(
        id = cartItemEntity.id,
        customerId = cartItemEntity.customerId,
        productId = cartItemEntity.productId,
        quantity = cartItemEntity.quantity,
        product = productEntity.asProduct(),
        createdAt = cartItemEntity.createdAt,
        updatedAt = cartItemEntity.updatedAt
    )
}

fun NetworkFavoriteProduct.asFavoriteProductEntity() =
    FavoriteProductEntity(
        id = id,
        customerId = customerId,
        productId = productId,
        createdAt = convertDateToLong(createdAt),
    )

fun FavoriteAndProduct.asFavoriteProduct(): FavoriteProduct {
    val favoriteProductEntity = this.favoriteProductEntity
    val productEntity = this.productEntity
    return FavoriteProduct(
        id = favoriteProductEntity.id,
        customerId = favoriteProductEntity.customerId,
        createdAt = favoriteProductEntity.createdAt,
        product = productEntity.asProduct()
    )
}
fun convertDateToLong(stringDate: String?): Long {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(stringDate.toString())?.time
        ?: Date().time
}

