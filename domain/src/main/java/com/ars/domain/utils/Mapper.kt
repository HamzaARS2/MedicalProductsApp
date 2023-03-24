package com.ars.domain.utils

import com.ars.domain.model.Category
import com.ars.domain.model.Customer
import com.ars.domain.model.FilterItem
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toCustomer(name: String): Customer =
    Customer(
        id = this.uid,
        name = name,
        email = this.email!!,
        phone = this.phoneNumber
    )

fun Category.asFilterItem(type: String) =
    FilterItem(
        id = id,
        name = name,
        type = type
    )