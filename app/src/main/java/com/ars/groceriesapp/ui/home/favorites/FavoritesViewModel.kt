package com.ars.groceriesapp.ui.home.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.CartItem
import com.ars.domain.model.Customer
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.usercase.cart.SaveMultipleCartItemsUseCase
import com.ars.domain.usercase.favorite_product.DeleteFavoriteProductUseCase
import com.ars.domain.usercase.favorite_product.GetFavoritesUseCase
import com.ars.domain.usercase.favorite_product.SaveFavoriteProductUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFavoriteProductUseCase: DeleteFavoriteProductUseCase,
    private val saveMultipleCartItemsUseCase: SaveMultipleCartItemsUseCase
) : ViewModel() {


    var customer = Customer()
    private val _favoriteProductsFlow: MutableStateFlow<Resource<List<FavoriteProduct>>?> =
        MutableStateFlow(null)
    val favoriteProductsFlow: StateFlow<Resource<List<FavoriteProduct>>?> get() = _favoriteProductsFlow


    fun fetchFavoriteProducts(id: String) = viewModelScope.launch {
        _favoriteProductsFlow.value = Resource.Loading
        _favoriteProductsFlow.emit(getFavoritesUseCase(id))
    }

    fun removeProductFromFavorites(
        id: Int,
        onSuccessDelete: () -> Unit,
        onDeleteFailed: (e: Exception) -> Unit
    ) = viewModelScope.launch {
        deleteFavoriteProductUseCase(id, onSuccessDelete, onDeleteFailed)
    }

    fun addMultipleCartItems(
        cartItems: List<CartItem>, onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) = viewModelScope.launch {
        saveMultipleCartItemsUseCase(cartItems, onSuccess, onFailure)
    }

}