package com.ars.data.repository

import android.util.Log
import com.ars.data.extensions.asCartItem
import com.ars.data.extensions.asCartItemEntity
import com.ars.data.local.GroceriesDatabase
import com.ars.data.local.relations.CartAndProduct
import com.ars.data.network.api.CartApi
import com.ars.data.network.model.NetworkCartItem
import com.ars.data.util.networkBoundResource
import com.ars.domain.model.CartItem
import com.ars.domain.repository.ICartRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApi: CartApi,
    private val database: GroceriesDatabase
) : ICartRepository {

    private val cartItemDao = database.getCartItemDao()

    override fun retrieveCustomerCartItems(id: String) = networkBoundResource(
        query = {
            cartItemDao.retrieveCartItems(id).map { cartAndProducts ->
                cartAndProducts.map { it.asCartItem() }
            }
        },
        fetch = {
            cartApi.getCustomerCartItems(id)
        },

        saveFetchResult = { networkCartItems ->
            cartItemDao.insertCartItems(networkCartItems.map { it.asCartItemEntity() })
        }
    )

    override fun saveCustomerCartItem(
        customerId: String,
        productId: Int
    ) = flow {
        emit(Response.Loading())
        val result = try {
            val count = cartItemDao.retrieveCartItemsCount(customerId, productId)
            if (count > 0) Response.Error(Throwable("Operation failed!"), "Already in your cart")
            else {
                val networkCartItem = cartApi.addItemToCustomerCart(customerId, productId)
                cartItemDao.insertCartItems(listOf(networkCartItem.asCartItemEntity()))
                Response.Success("Added to cart")
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable, "Something went wrong!, please try again")
        }
        emit(result)
    }

    override fun saveMultipleCartItems(
        customerId: String,
        productIds: IntArray
    ) = flow {
        val result = try {
            val networkCartItems = cartApi.saveMultipleItemsToCustomerCart(customerId, productIds)
            cartItemDao.insertCartItems(networkCartItems.map { it.asCartItemEntity() })
            Response.Success("Added to cart")
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable, "Something went wrong!, please check your internet connection")
        }
        emit(result)
    }


    override fun deleteItemFromCart(
        id: Int
    ) = flow {
        emit(Response.Loading())
        val result = try {
            cartApi.deleteCustomerCartItem(id)
            cartItemDao.deleteCartItem(id)
            Response.Success("Deleted from cart")
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable,"Something went wrong!, please check your internet connection")
        }

        emit(result)
    }

    override suspend fun updateCartItemQuantity(id: Int, quantity: Int) {
        cartItemDao.updateCartItemQuantity(id, quantity)
    }


}