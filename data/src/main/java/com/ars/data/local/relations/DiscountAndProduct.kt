package com.ars.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ars.data.local.entity.DiscountEntity
import com.ars.data.local.entity.ProductEntity

data class DiscountAndProduct(
    @Embedded
    val productEntity: ProductEntity,

    @Relation(
        parentColumn = "product_discount_id",
        entityColumn = "discount_id",
        entity = DiscountEntity::class
    )
    val discountEntity: DiscountEntity?
)
