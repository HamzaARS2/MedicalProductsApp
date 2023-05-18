package com.ars.groceriesapp.ui.home.cart

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ars.domain.model.CartItem
import com.ars.groceriesapp.ui.home.HomeViewModel
import kotlin.math.min

class CartManager(
    private var items: List<CartItem>,
    private val cartViewModel: CartViewModel,
    homeViewModel: HomeViewModel,
    lifecycleOwner: LifecycleOwner
) {

    private var minQuantity = 1

    fun setItems(data: List<CartItem>) {
        this.items = data
    }

    fun getItemsIds() = this.items.map { it.id }

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
            if (quantity <= minQuantity) quantity = minQuantity
            else quantity -= 1
        }
        _cartItem.value = updatedItem to position
        _totalPrice.value = calculateTotalPrice()
        return updatedItem.quantity
    }

    fun refreshTotalPrice() {
        _totalPrice.value = calculateTotalPrice()
    }

    fun setMinQuantity(quantity: Int) {
        minQuantity = quantity
    }

    fun getMinQuantity() = minQuantity


}