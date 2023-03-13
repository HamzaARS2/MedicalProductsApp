package com.ars.data.local.dao

import androidx.room.*
import com.ars.data.local.entity.ProductEntity
import com.ars.data.local.relation.DiscountAndProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM product")
    fun retrieveProducts(): Flow<List<ProductEntity>>

    @Transaction
    @Query("SELECT * FROM product")
    fun getProductsWithDiscount(): Flow<List<DiscountAndProduct>>


    @Query("DELETE FROM product")
    fun deleteAllProducts()
}