package com.ars.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Location(
    val id: Int,
    val latitude: BigDecimal,
    val longitude: BigDecimal
): Parcelable
