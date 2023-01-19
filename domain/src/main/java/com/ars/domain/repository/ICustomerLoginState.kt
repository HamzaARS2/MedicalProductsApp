package com.ars.domain.repository

import com.google.firebase.auth.FirebaseUser

interface ICustomerLoginState {
    val currentUser: FirebaseUser?
    fun isLoggedIn(): Boolean
}