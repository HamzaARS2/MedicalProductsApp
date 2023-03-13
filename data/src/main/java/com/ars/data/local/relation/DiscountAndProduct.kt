package com.ars.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.ars.data.local.entity.DiscountEntity
import com.ars.data.local.entity.ProductEntity

data class DiscountAndProduct(
    @Embedded
    val productEntity: ProductEntity,

    @Relation(
        parentColumn = "productDiscountId",
        entityColumn = "discountId"
    )
    val discountEntity: DiscountEntity?
)
