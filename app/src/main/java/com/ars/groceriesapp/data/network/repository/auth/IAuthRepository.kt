package com.ars.groceriesapp.data.network.repository.auth

import com.ars.domain.Resource
import com.google.firebase.auth.FirebaseUser
import com.ars.groceriesapp.model.Customer
import com.ars.groceriesapp.data.network.repository.ICRUDRepository

interface IAuthRepository : ICRUDRepository<Customer, String> {
    val currentUser: FirebaseUser?
    suspend fun register(name: String, email: String, password: String) : Resource<Customer>
    suspend fun login(email: String, password: String): Resource<Customer?>
    fun logout()
}