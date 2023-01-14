package com.ars.groceriesapp.utils

import com.google.firebase.auth.FirebaseUser
import com.ars.groceriesapp.model.Customer

fun FirebaseUser.toCustomer(name: String): Customer =
    Customer(
        docId = this.uid,
        name = name,
        email = this.email.toString(),
        phone = "0607439431",
        address = "Tetouan boussafou R 11 N 6"
    )



