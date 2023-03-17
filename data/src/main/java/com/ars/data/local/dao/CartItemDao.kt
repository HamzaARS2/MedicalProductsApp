package com.ars.data.local.dao

import androidx.room.*
import com.ars.data.local.entity.CartItemEntity
import com.ars.data.local.relations.CartAndProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {


    @Transaction
    @Query("SELECT * FROM cart_items WHERE customerId =:id")
    fun retrieveCartItems(id: String): Flow<List<CartAndProduct>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCartItems(cartItemEntity: List<CartItemEntity>)

    @Query("DELETE FROM cart_items WHERE id = :deletedItemId")
    suspend fun deleteCartItem(deletedItemId: Int)

    @Query("SELECT COUNT(*) FROM cart_items WHERE customerId =:customerId AND productId =:productId")
    suspend fun retrieveCartItemsCount(customerId: String, productId: Int): Int

    @Query("UPDATE cart_items SET quantity =:quantity WHERE id =:itemId")
    suspend fun updateCartItemQuantity(itemId: Int, quantity: Int)

}