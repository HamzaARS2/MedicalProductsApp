package com.ars.data.repository

import com.ars.data.extensions.asReview
import com.ars.data.network.api.ReviewApi
import com.ars.domain.model.ProductReview
import com.ars.domain.model.Review
import com.ars.domain.model.ReviewRequest
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReviewsRepository @Inject constructor(
    private val reviewsApi: ReviewApi
) {


    suspend fun fetchProductReviews(productId: Int): Resource<List<Review>> {
        return try {
            val response =
                reviewsApi.getReviewsByProductId(productId).map { it.asReview() }
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    fun saveReview(productId: Int, customerId: String, rating: Float, comment: String) = flow {
        emit(Response.Loading())
        val result = try {
            val reviewRequest = ReviewRequest(productId, customerId, rating, comment)
            val response = reviewsApi.saveReview(reviewRequest)
            Response.Success(response)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable)
        }
        emit(result)
    }

}