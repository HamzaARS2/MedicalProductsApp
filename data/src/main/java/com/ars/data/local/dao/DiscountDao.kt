package com.ars.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ars.data.local.entity.DiscountEntity

@Dao
interface DiscountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiscounts(discounts: List<DiscountEntity>)

    @Query("DELETE FROM discount")
    fun deleteDiscounts()
}