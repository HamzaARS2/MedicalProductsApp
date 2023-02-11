package com.ars.data.repository.customer

import android.annotation.SuppressLint
import com.ars.data.remote.CustomerDataSource
import com.ars.data.remote.api.CustomerApi
import com.ars.data.repository.auth.LoginRepository
import com.ars.data.repository.auth.RegistrationRepository
import com.ars.domain.model.Customer
import com.ars.domain.repository.customer.ICustomerRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.toCustomer
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.log

class CustomerRepositoryImpl @Inject constructor(
    private val customerDataSource: CustomerDataSource,
    private val loginRepo: LoginRepository,
    private val registrationRepo: RegistrationRepository
) : ICustomerRepository {
    override val isLoggedIn: Pair<Boolean,String?>
        get() = loginRepo.isLoggedIn


    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Resource<Customer> {
        val authResource = registrationRepo.createNewCustomer(email, password)
        return if (authResource is Resource.Success) {
            val user = authResource.result
            val customer = user.toCustomer(username)
            customerDataSource.insert(customer)
        } else authResource as Resource.Failure
    }

    override suspend fun login(email: String, password: String): Resource<Customer> {
        val loginResource = loginRepo.signInCustomer(email, password)
        return if (loginResource is Resource.Success) {
            val user = loginResource.result
            customerDataSource.retrieve(user.uid)
        } else loginResource as Resource.Failure
    }

    override suspend fun update(customer: Customer): Resource<Customer> {
        return customerDataSource.update(customer)
    }

    override suspend fun getCustomer(id: String): Resource<Customer> {
        return customerDataSource.retrieve(id)
    }


    override suspend fun linkPhoneWithExistingAccount(
        verificationId: String,
        smsCode: String,
        onSuccess: (phone: String) -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        val registrationResource = registrationRepo.linkPhoneWithExistingAccount(verificationId, smsCode)
        if (registrationResource is Resource.Success) {
            val user = registrationResource.result
            if (user != null) onSuccess(user.phoneNumber ?: "")
            else onFailure(NullPointerException("Please re-authenticate"))
        } else registrationResource as Resource.Failure
    }

    override fun logOut() {
        loginRepo.logout()
    }


}