package com.ars.groceriesapp.ui.home.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.CartItem
import com.ars.domain.model.Customer
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.model.Product
import com.ars.domain.usercase.cart.SaveMultipleCartItemUseCase
import com.ars.domain.usercase.favorite_product.DeleteFavoriteProductUseCase
import com.ars.domain.usercase.favorite_product.GetFavoritesUseCase
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
    private val saveMultipleCartItemUseCase: SaveMultipleCartItemUseCase
) : ViewModel() {


    var customer = Customer()
    private val _favoriteProductsFlow: MutableStateFlow<Resource<List<FavoriteProduct>>?> =
        MutableStateFlow(null)
    val favoriteProductsFlow: StateFlow<Resource<List<FavoriteProduct>>?> get() = _favoriteProductsFlow


    fun fetchFavoriteProducts(id: String) =
        getFavoritesUseCase(id).asLiveData()

    fun saveMultipleCartItems(customerId: String, products: List<FavoriteProduct>) =
        saveMultipleCartItemUseCase(customerId, products).asLiveData()


    fun removeProductFromFavorites(
        customerId: String,
        productId: Int
    ) = deleteFavoriteProductUseCase(customerId, productId).asLiveData()



}