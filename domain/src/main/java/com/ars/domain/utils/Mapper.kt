package com.ars.domain.utils

import com.ars.domain.model.Category
import com.ars.domain.model.Customer
import com.ars.groceriesapp.ui.home.search.filter.FilterCategory
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toCustomer(name: String): Customer =
    Customer(
        docId = this.uid,
        name = name,
        email = this.email!!,
        phone = this.phoneNumber ?: "",
        address = ""
    )

fun Category.toFilterCategory() =
    com.ars.groceriesapp.ui.home.search.filter.FilterCategory(
        id = id,
        name = name
    )