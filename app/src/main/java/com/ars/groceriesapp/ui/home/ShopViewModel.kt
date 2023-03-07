package com.ars.groceriesapp.ui.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.CategoryRepository
import com.ars.data.repository.auth.LoginRepository
import com.ars.domain.model.*
import com.ars.domain.usercase.cart.SaveCartItemUseCase
import com.ars.domain.usercase.favorite_product.SaveFavoriteProductUseCase
import com.ars.domain.utils.Resource
import com.ars.domain.usercase.product.GetExclusiveProductsUseCase
import com.ars.domain.usercase.product.GetMostRatedProductUseCase
import com.ars.domain.usercase.product.GetOnSaleProductsUseCae
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val getExclusiveProductsUseCase: GetExclusiveProductsUseCase,
    private val getMostRatedProductUseCase: GetMostRatedProductUseCase,
    private val getOnSaleProductsUseCae: GetOnSaleProductsUseCae,
    private val saveCartItemUseCase: SaveCartItemUseCase,
    private val saveFavoriteProductUseCase: SaveFavoriteProductUseCase,
    private val loginRepo: LoginRepository,
    private val categoryRepo: CategoryRepository
) : ViewModel() {


    private val _exclusivesFlow: MutableStateFlow<Resource<List<Product>?>?> =
        MutableStateFlow(null)
    val exclusivesFlow: StateFlow<Resource<List<Product>?>?> get() = _exclusivesFlow

    private val _onSaleProductsFlow: MutableStateFlow<Resource<List<OnSaleProduct>?>?> =
        MutableStateFlow(null)
    val onSaleProductsFlow: StateFlow<Resource<List<OnSaleProduct>?>?> get() = _onSaleProductsFlow

    private val _mostRatedFlow: MutableStateFlow<Resource<List<Product>?>?> = MutableStateFlow(null)
    val mostRatedFlow: StateFlow<Resource<List<Product>?>?> get() = _mostRatedFlow

    private val _categoriesFlow: MutableStateFlow<Resource<List<Category>?>?> =
        MutableStateFlow(null)
    val categoriesFlow: StateFlow<Resource<List<Category>?>?> get() = _categoriesFlow

    init {
        fetchProducts()
    }


    private fun fetchProducts() = viewModelScope.launch {

        // Exclusive Products
        _exclusivesFlow.value = Resource.Loading
        val exclusivesResponse = async { getExclusiveProductsUseCase() }

        // OnSale Products
        _onSaleProductsFlow.value = Resource.Loading
        val onSaleResponse = async { getOnSaleProductsUseCae() }

        // Most Rated Products
        _mostRatedFlow.value = Resource.Loading
        val mostRatedResponse = async { getMostRatedProductUseCase() }

        // Categories
        _categoriesFlow.value = Resource.Loading
        val categoriesResponse = async {
            categoryRepo.retrieveAll()
        }


        _exclusivesFlow.emit(exclusivesResponse.await())
        _categoriesFlow.emit(categoriesResponse.await())
        _onSaleProductsFlow.emit(onSaleResponse.await())
        _mostRatedFlow.emit(mostRatedResponse.await())

    }

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