package com.ars.groceriesapp.ui.home.shop


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.auth.LoginRepository
import com.ars.domain.model.*
import com.ars.domain.usercase.GetShopContentUseCase
import com.ars.domain.usercase.cart.SaveCartItemUseCase
import com.ars.domain.usercase.favorite_product.SaveFavoriteProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val saveCartItemUseCase: SaveCartItemUseCase,
    getShopContentUseCase: GetShopContentUseCase
) : ViewModel() {

    val productsAndCategories = getShopContentUseCase().asLiveData()



    fun saveCartItem(customerId: String, productId: Int) =
        saveCartItemUseCase(customerId, productId).asLiveData()


}