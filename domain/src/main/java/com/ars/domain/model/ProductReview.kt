package com.ars.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class ProductReview(
    val id: Int,
    val name: String,
    val price: BigDecimal,
    val priceUnit: String,
    val image: String,
    val customerRating: Float,
    val customerComment: String,
    val reviewed: Boolean
): Parcelable
