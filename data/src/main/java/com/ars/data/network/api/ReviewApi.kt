package com.ars.data.network.api

import com.ars.data.network.model.NetworkReview
import com.ars.domain.model.ReviewRequest
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApi {
    @GET("reviews/product/{id}")
    suspend fun getReviewsByProductId(@Path("id")id: Int): List<NetworkReview>

    @POST("reviews/save")
    suspend fun saveReview(@Body reviewRequest: ReviewRequest): NetworkReview
}