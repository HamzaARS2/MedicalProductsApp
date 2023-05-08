package com.ars.domain.repository.customer

import com.ars.domain.model.Customer
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface ICustomerRepository {

    val isLoggedIn: Pair<Boolean,String?>
    suspend fun register(username: String, email: String, password: String): Resource<Customer>
    fun login(email: String, password: String): Flow<Response<Customer?>>

    fun update(customer: Customer): Flow<Response<Customer>>

    fun getCustomer(id: String): Flow<Response<Customer?>>

    suspend fun linkPhoneWithExistingAccount(
        verificationId: String, smsCode: String, onSuccess: (phone: String) -> Unit,
        onFailure: (e: Exception) -> Unit
    )

    fun logOut()


}