package com.ars.domain.utils

import com.ars.domain.model.Customer
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toCustomer(name: String,phone: String, address: String): Customer =
    Customer(
        docId = this.uid,
        name = name,
        email = this.email!!,
        phone = phone,
        address = address
    )