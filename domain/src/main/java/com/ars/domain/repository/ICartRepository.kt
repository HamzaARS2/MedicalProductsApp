package com.ars.domain.repository

import com.ars.domain.model.CartItem
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface ICartRepository {

    fun retrieveCustomerCartItems(id: String): Flow<Response<List<CartItem>>>
    fun saveCustomerCartItem(
        customerId: String,
        productId: Int
    ): Flow<Response<String>>

    fun saveMultipleCartItems(customerId: String, productIds: IntArray): Flow<Response<String>>


    fun deleteItemFromCart(id: Int): Flow<Response<String>>

    suspend fun updateCartItemQuantity(id: Int, quantity: Int)

}