package com.ars.domain.repository.customer

import com.ars.domain.model.Customer
import com.ars.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ICustomerRepository {

    val isLoggedIn: Pair<Boolean,String?>
    suspend fun register(username: String, email: String, password: String): Resource<Customer>
    suspend fun login(email: String, password: String): Resource<Customer>

    suspend fun update(customer: Customer): Resource<Customer>

    suspend fun getCustomer(id: String): Resource<Customer>

    suspend fun linkPhoneWithExistingAccount(
        verificationId: String, smsCode: String, onSuccess: (phone: String) -> Unit,
        onFailure: (e: Exception) -> Unit
    )

    fun logOut()


}