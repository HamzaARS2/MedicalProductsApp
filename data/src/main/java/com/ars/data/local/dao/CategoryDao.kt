package com.ars.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ars.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM category")
    fun retrieveCategories(): Flow<List<CategoryEntity>>

    @Query("DELETE FROM category")
    fun deleteCategories()

}