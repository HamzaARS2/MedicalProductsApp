package com.ars.groceriesapp.ui.home.product_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.ProductDetails
import com.ars.domain.usercase.cart.SaveCartItemUseCase
import com.ars.domain.usercase.favorite_product.DeleteFavoriteProductUseCase
import com.ars.domain.usercase.favorite_product.SaveFavoriteProductUseCase
import com.ars.domain.usercase.product.GetProductDetailsUseCase
import com.ars.domain.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val saveCartItemUseCase: SaveCartItemUseCase,
    private val saveFavoriteProductUseCase: SaveFavoriteProductUseCase,
    private val deleteFavoriteProductUseCase: DeleteFavoriteProductUseCase
) : ViewModel() {

    private val _productDetails = MutableLiveData<Response<ProductDetails>>()
    val liveProductDetails: LiveData<Response<ProductDetails>> get() = _productDetails
    fun getProductDetails(customerId: String?, productId: Int) {
        viewModelScope.launch {
            getProductDetailsUseCase(customerId, productId).collectLatest {
                _productDetails.postValue(it)
            }
        }
    }

    fun saveCartItem(customerId: String, productId: Int) =
        saveCartItemUseCase(customerId, productId).asLiveData()

    fun saveFavoriteProduct(
        productId: Int,
        customerId: String
    ) = saveFavoriteProductUseCase(productId, customerId).asLiveData()

    fun removeProductFromFavorites(
        customerId: String,
        productId: Int
    ) = deleteFavoriteProductUseCase(customerId, productId).asLiveData()


}