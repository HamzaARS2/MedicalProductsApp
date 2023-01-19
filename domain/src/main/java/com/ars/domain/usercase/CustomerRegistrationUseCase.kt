package com.ars.domain.usercase

import com.ars.domain.utils.Resource
import com.ars.domain.model.Customer
import com.ars.domain.repository.IAuthRepository
import com.ars.domain.repository.ICustomerRepository
import com.ars.domain.utils.toCustomer
import java.lang.Exception
import javax.inject.Inject

class CustomerRegistrationUseCase @Inject constructor(
    private val firebaseAuth: IAuthRepository,
    private val customerRepository: ICustomerRepository
) {

    suspend fun registerCustomer(
        name: String,
        email: String,
        password: String,
        phone: String,
        address: String
    ): Resource<Customer> {
        val authResource = firebaseAuth.register(email, password)
        return if (authResource is Resource.Success) {
            val user = authResource.result
            val customer = user.toCustomer(name, phone, address)
            val response = customerRepository.insert(customer)
            Resource.Success(response)
        } else authResource as Resource.Failure
    }

    suspend fun loginCustomer(
        email: String,
        password: String
    ): Resource<Customer> {
        val authResource = firebaseAuth.login(email, password)
        return if (authResource is Resource.Success) {
            val user = authResource.result
            val customer = user?.uid?.let { customerRepository.retrieve(it) }
            if (customer != null)
            Resource.Success(customer)
            else Resource.Failure(Exception("Customer not found"))
        } else authResource as Resource.Failure
    }

    fun setLoginStateListener(onLoginStateChanged: (loggedIn: Boolean) -> Unit) {
        firebaseAuth.loginState(onLoginStateChanged)
    }

    fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null

    fun logoutCustomer() {
        firebaseAuth.logout()
    }
}