package com.ars.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ars.data.local.entity.CartItemEntity
import com.ars.data.local.entity.ProductEntity

data class CartAndProduct(
    @Embedded val cartItemEntity: CartItemEntity,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
        entity = ProductEntity::class
    )
    val productEntity: ProductEntity
)
