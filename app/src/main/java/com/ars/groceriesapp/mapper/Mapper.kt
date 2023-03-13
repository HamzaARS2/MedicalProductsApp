package com.ars.groceriesapp.mapper

import com.ars.domain.model.CartItem
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.model.Product
import com.ars.domain.model.ProductDetails

fun FavoriteProduct.toCartItem() =
    CartItem(
        customerId = this.customerId,
        productId = this.productId,
        quantity = 1,
    )

fun Product.toCartItem(customerId: String) =
    CartItem(
        customerId = customerId,
        productId = this.id,
        quantity = 1,
    )

fun Product.toFavoriteProduct(customerId: String) =
    FavoriteProduct(
        customerId = customerId,
        productId = this.id
    )