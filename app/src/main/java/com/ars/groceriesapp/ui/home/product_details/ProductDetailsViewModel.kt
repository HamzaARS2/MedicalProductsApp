package com.ars.groceriesapp.ui.home.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.ReviewsRepository
import com.ars.domain.model.CartItem
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.model.ProductDetails
import com.ars.domain.model.Review
import com.ars.domain.repository.IFavoritesRepository
import com.ars.domain.usercase.cart.DeleteCartItemUseCase
import com.ars.domain.usercase.cart.SaveCartItemUseCase
import com.ars.domain.usercase.favorite_product.DeleteFavoriteProductUseCase
import com.ars.domain.usercase.favorite_product.SaveFavoriteProductUseCase
import com.ars.domain.usercase.product.GetProductDetailsUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val saveCartItemUseCase: SaveCartItemUseCase,
    private val saveFavoriteProductUseCase: SaveFavoriteProductUseCase,
    private val deleteFavoriteProductUseCase: DeleteFavoriteProductUseCase,
    private val favoriteRepository: IFavoritesRepository,
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    private val _productDetailsFlow: MutableStateFlow<Resource<ProductDetails>?> =
        MutableStateFlow(null)
    val productDetailsFlow: StateFlow<Resource<ProductDetails>?> get() = _productDetailsFlow

    private val _reviewsFlow: MutableStateFlow<Resource<List<Review>>?> = MutableStateFlow(null)
    val reviewsFlow: StateFlow<Resource<List<Review>>?> get() = _reviewsFlow

    fun getProductDetails(id: Int) = viewModelScope.launch {
        _productDetailsFlow.value = Resource.Loading
        _productDetailsFlow.emit(getProductDetailsUseCase(id))
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

    fun removeProductFromFavorites(
        customerId: String,
        productId: Int,
        onSuccessDelete: () -> Unit,
        onDeleteFailed: (e: Exception) -> Unit
    ) = viewModelScope.launch {
        favoriteRepository.deleteProductFromFavorites(
            customerId,
            productId,
            onSuccessDelete,
            onDeleteFailed
        )
    }

    fun getProductReviews(productId: Int) = viewModelScope.launch {
        _reviewsFlow.value = Resource.Loading
        _reviewsFlow.emit(reviewsRepository.fetchProductReviews(productId))
    }


}