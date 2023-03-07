package com.ars.groceriesapp.mapper

import com.ars.domain.model.CartItem
import com.ars.domain.model.FavoriteProduct

fun FavoriteProduct.toCartItem() =
    CartItem(
        customerId = this.customerId,
        productId = this.productId,
        quantity = 1,
    )