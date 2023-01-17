package com.ars.domain.repository

import com.ars.domain.model.Customer
import com.ars.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface IAuthRepository {
    val currentUser: FirebaseUser?
    suspend fun register( email: String, password: String): Resource<FirebaseUser>
    suspend fun login(email: String, password: String): Resource<FirebaseUser?>
    fun loginState(onLoginStateChanged: (loggedIn: Boolean) -> Unit)
    suspend fun delete(): Boolean
    fun logout()
}