package com.ars.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ars.data.local.entity.FavoriteProductEntity
import com.ars.data.local.entity.ProductEntity

data class FavoriteAndProduct(
    @Embedded val favoriteProductEntity: FavoriteProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val productEntity: ProductEntity

)
