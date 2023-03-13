package com.ars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ars.data.local.dao.DiscountDao
import com.ars.data.local.dao.ProductDao
import com.ars.data.local.entity.DiscountEntity
import com.ars.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class, DiscountEntity::class], version = 1, exportSchema = false)
abstract class GroceriesDatabase: RoomDatabase() {

    abstract fun getProductDao(): ProductDao
    abstract fun getDiscountDao(): DiscountDao

}