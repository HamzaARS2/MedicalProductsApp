package com.ars.data.network.api

import com.ars.data.network.network_model.NetworkReview
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewApi {
    @GET("reviews/product/{id}")
    suspend fun getReviewsByProductId(@Path("id")id: Int): List<NetworkReview>
}