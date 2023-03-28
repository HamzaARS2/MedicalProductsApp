package com.ars.domain.utils

import com.ars.domain.model.*
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toCustomer(name: String): Customer =
    Customer(
        id = this.uid,
        name = name,
        email = this.email!!,
        phone = this.phoneNumber
    )

fun CartItem.asOrderItem() =
    OrderItem(
        productId = product!!.id,
        productName = product.name,
        productImage = product.image,
        productUnitPrice = product.priceUnit,
        quantity = quantity,
        subTotalPrice = product.price.times(quantity.toBigDecimal())
    )