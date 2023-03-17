package com.ars.groceriesapp.ui.home.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ars.domain.model.CartItem

class CartManager(
    private val items: List<CartItem>
) {
    private fun calculateTotalPrice(): String {
        return "$" + items.sumOf { item ->
            (item.product!!.price).times(item.quantity.toBigDecimal())
        }
    }
    private val _totalPrice: MutableLiveData<String> =
        MutableLiveData(calculateTotalPrice())
    val totalPrice: LiveData<String> get() = _totalPrice

    private val _cartItem: MutableLiveData<Pair<CartItem, Int>> = MutableLiveData()
    val cartItem: LiveData<Pair<CartItem, Int>> get() = _cartItem

    fun increaseQuantity(position: Int): Int {
        val updatedItem = items[position].apply {
            quantity += 1
        }
        _cartItem.value = updatedItem to position
        _totalPrice.value = calculateTotalPrice()
        return updatedItem.quantity
    }

    fun decreaseQuantity(position: Int): Int {
        val updatedItem = items[position].apply {
            if (quantity <= 1) quantity = 1
            else quantity -= 1
        }
        _cartItem.value = updatedItem to position
        _totalPrice.value = calculateTotalPrice()
        return updatedItem.quantity
    }




}