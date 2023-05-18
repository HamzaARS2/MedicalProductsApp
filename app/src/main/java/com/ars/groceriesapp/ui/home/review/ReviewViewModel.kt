package com.ars.groceriesapp.ui.home.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ars.data.repository.ReviewsRepository
import com.ars.data.repository.product.ProductRepositoryImpl
import com.ars.domain.model.ProductReview
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl,
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    fun getOrderProducts(orderId: Int) =
        productRepositoryImpl.getOrderProducts(orderId).asLiveData()

    fun saveReview(productId: Int, customerId: String, rating: Float, comment: String) =
        reviewsRepository.saveReview(productId, customerId, rating, comment).asLiveData()


}