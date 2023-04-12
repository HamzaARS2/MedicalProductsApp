package com.ars.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val id: String,
    val name: String,
    val email: String,
    var phone: String?,
    var address: Address? = null,
    var location: Location? = null
): Parcelable
