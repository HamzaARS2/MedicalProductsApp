package com.ars.data.network.model


import com.google.gson.annotations.SerializedName

data class NetworkProductDetails(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    val priceUnit: String,
    val rating: Float,
    val exclusive: Boolean,
    val discountId: Int?,
    @SerializedName("discount")
    val networkDiscount: NetworkDiscount?,
    val reviews: List<NetworkReview>?,
    val similarProducts: List<NetworkProduct>?
)




