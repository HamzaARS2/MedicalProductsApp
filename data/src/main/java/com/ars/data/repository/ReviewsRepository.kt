package com.ars.data.repository

import com.ars.data.extensions.asReview
import com.ars.data.network.api.ReviewApi
import com.ars.domain.model.Review
import com.ars.domain.utils.Resource
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

}