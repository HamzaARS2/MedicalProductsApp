package com.ars.data.network.model

import com.ars.domain.model.Customer
import com.google.gson.annotations.SerializedName

data class NetworkReview(
    val id: Int,
    val productId: Int,
    val customerId: String,
    val comment: String,
    val rating: Float,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("customer")
    val networkCustomer: NetworkCustomer
)
