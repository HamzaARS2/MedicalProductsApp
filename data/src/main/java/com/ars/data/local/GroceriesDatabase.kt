package com.ars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ars.data.local.dao.*
import com.ars.data.local.entity.*

@Database(
    entities = [ProductEntity::class, DiscountEntity::class, CategoryEntity::class,
        CartItemEntity::class, FavoriteProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GroceriesDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao
    abstract fun getDiscountDao(): DiscountDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getCartItemDao(): CartItemDao
    abstract fun getFavoriteProductDao(): FavoriteProductDao

}