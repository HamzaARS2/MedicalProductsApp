package com.ars.domain.repository.customer

import com.google.firebase.auth.FirebaseUser

interface ICustomerLoginState {
    val currentUser: FirebaseUser?
    fun isLoggedIn(): Boolean
}