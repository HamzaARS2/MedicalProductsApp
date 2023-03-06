package com.ars.domain.repository

import com.ars.domain.model.CartItem
import com.ars.domain.utils.Resource

interface ICartRepository {

    suspend fun retrieveCustomerCartItems(id: String): Resource<List<CartItem>>
    suspend fun saveCustomerCartItem(cartItem: CartItem, onSuccess: () -> Unit, onFailure: (e: Exception) -> Unit)
    suspend fun deleteItemFromCart(id: Int, onSuccessDelete:() -> Unit, onDeleteFailed: (e: Exception) -> Unit)

}