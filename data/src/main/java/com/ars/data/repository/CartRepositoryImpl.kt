package com.ars.data.repository

import com.ars.data.network.api.CartApi
import com.ars.domain.model.CartItem
import com.ars.domain.repository.ICartRepository
import com.ars.domain.utils.Resource
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApi: CartApi
) : ICartRepository {


    override suspend fun retrieveCustomerCartItems(id: String): Resource<List<CartItem>> {
        return try {
            val response = cartApi.getCustomerCartItems(id)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun saveCustomerCartItem(
        cartItem: CartItem,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        try {
            cartApi.addItemToCustomerCart(cartItem)
            onSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e)
        }
    }

    override suspend fun saveMultipleCustomerCartItems(
        cartItems: List<CartItem>,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        try {
            cartApi.addMultipleItemsToCustomerCart(cartItems)
            onSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e)
        }
    }

    override suspend fun deleteItemFromCart(
        id: Int,
        onSuccessDelete: () -> Unit,
        onDeleteFailed: (e: Exception) -> Unit
    ) {
        try {
            cartApi.deleteCustomerCartItem(id)
            onSuccessDelete()
        } catch (e: Exception) {
            e.printStackTrace()
            onDeleteFailed(e)
        }
    }


}