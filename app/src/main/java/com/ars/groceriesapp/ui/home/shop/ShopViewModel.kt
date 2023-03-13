package com.ars.groceriesapp.ui.home.shop


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.CategoryRepository
import com.ars.data.repository.auth.LoginRepository
import com.ars.data.repository.product.ProductRepositoryImpl
import com.ars.domain.model.*
import com.ars.domain.repository.product.IProductRepository
import com.ars.domain.usercase.GetShopItemsUseCase
import com.ars.domain.usercase.cart.SaveCartItemUseCase
import com.ars.domain.usercase.favorite_product.SaveFavoriteProductUseCase
import com.ars.domain.usercase.product.GetShopProductsUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val saveCartItemUseCase: SaveCartItemUseCase,
    private val saveFavoriteProductUseCase: SaveFavoriteProductUseCase,
    private val loginRepo: LoginRepository,
    private val getShopItemsUseCase: GetShopItemsUseCase
) : ViewModel() {

    val productsAndCategories = getShopItemsUseCase().asLiveData()



    fun saveCartItem(cartItem: CartItem, onSuccess: () -> Unit, onFailure: (e: Exception) -> Unit) =
        viewModelScope.launch {
            saveCartItemUseCase(cartItem, onSuccess, onFailure)
        }

    fun saveFavoriteProduct(
        favoriteProduct: FavoriteProduct,
        onSuccessSave: () -> Unit,
        onSaveFailed: (e: Exception) -> Unit
    ) = viewModelScope.launch {
        saveFavoriteProductUseCase(favoriteProduct, onSuccessSave, onSaveFailed)
    }


    fun logOut() = loginRepo.logout()


}