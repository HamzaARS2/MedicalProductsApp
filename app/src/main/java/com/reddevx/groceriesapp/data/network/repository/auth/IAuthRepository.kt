package com.reddevx.groceriesapp.data.network.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.reddevx.groceriesapp.model.Customer
import com.reddevx.groceriesapp.data.network.Resource
import com.reddevx.groceriesapp.data.network.repository.IRepository
import kotlinx.coroutines.flow.StateFlow

interface IAuthRepository : IRepository<Customer, String> {
    val currentUser: FirebaseUser?
    suspend fun register(name: String, email: String, password: String) : Resource<Customer>
    suspend fun login(email: String, password: String): Resource<Customer?>
    fun logout()
}