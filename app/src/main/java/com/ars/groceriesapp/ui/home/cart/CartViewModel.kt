package com.ars.groceriesapp.ui.home.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.CartItem
import com.ars.domain.usercase.cart.DeleteCartItemUseCase
import com.ars.domain.usercase.cart.GetCartItemsUseCase
import com.ars.domain.usercase.cart.UpdateCartItemUseCase
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase
) : ViewModel() {

    var customerId = ""

    private val _cartItems: MutableLiveData<Response<List<CartItem>>> = MutableLiveData()
    val cartItems: LiveData<Response<List<CartItem>>> get() = _cartItems



    fun getCustomerCartItems(id: String) = viewModelScope.launch {
        getCartItemsUseCase(id).collectLatest { response ->
            _cartItems.postValue(response)
        }
    }

    fun removeItemFromCart(id: Int) =
        deleteCartItemUseCase(id).asLiveData()

    fun updateCartItemQuantity(id: Int, quantity: Int) = viewModelScope.launch {
        updateCartItemUseCase(id, quantity)
    }

}