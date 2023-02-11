package com.ars.domain.utils

import com.ars.domain.model.Customer
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toCustomer(name: String): Customer =
    Customer(
        docId = this.uid,
        name = name,
        email = this.email!!,
        phone = this.phoneNumber ?: "",
        address = ""
    )