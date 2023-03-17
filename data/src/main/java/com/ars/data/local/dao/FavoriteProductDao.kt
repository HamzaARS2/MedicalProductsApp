package com.ars.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ars.data.local.entity.FavoriteProductEntity
import com.ars.data.local.relations.FavoriteAndProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {

    @Query("SELECT * FROM favorite_products WHERE customerId =:id")
    fun retrieveFavoriteProducts(id: String): Flow<List<FavoriteAndProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProduct(favoriteProductEntity: List<FavoriteProductEntity>)

    @Query("DELETE FROM favorite_products WHERE productId =:productId AND customerId = :customerId")
    suspend fun deleteFavoriteProduct(productId: Int, customerId: String)

    @Query("SELECT COUNT(*) FROM favorite_products WHERE customerId =:customerId AND productId =:productId")
    suspend fun retrieveFavoriteProductCount(customerId: String, productId: Int): Int

}