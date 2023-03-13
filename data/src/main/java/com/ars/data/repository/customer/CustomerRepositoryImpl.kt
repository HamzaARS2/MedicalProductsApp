package com.ars.data.repository.customer

import android.content.SharedPreferences
import android.util.Log
import com.ars.data.network.CustomerDataSource
import com.ars.data.repository.LocalCustomerRepository
import com.ars.data.repository.auth.LoginRepository
import com.ars.data.repository.auth.RegistrationRepository
import com.ars.data.util.networkBoundResource
import com.ars.domain.model.Customer
import com.ars.domain.repository.customer.ICustomerRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import com.ars.domain.utils.toCustomer
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val customerDataSource: CustomerDataSource,
    private val loginRepo: LoginRepository,
    private val registrationRepo: RegistrationRepository,
    private val sharedPreferences: SharedPreferences,
    private val localCustomerRepo: LocalCustomerRepository
) : ICustomerRepository {
    override val isLoggedIn: Pair<Boolean, String?>
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

    override fun login(email: String, password: String): Flow<Response<Customer>> = flow {
        val loginResponse = loginRepo.signInCustomer(email, password).first()
        val result =  if (loginResponse is Response.Success) {
            val user = loginResponse.data
            getCustomer(user!!.uid)
        } else flowOf(Response.Error(Throwable("Something went wrong!")))
        emitAll(result)
    }

    override suspend fun update(customer: Customer): Resource<Customer> {
        return customerDataSource.update(customer)
    }

    override fun getCustomer(id: String): Flow<Response<Customer>> =
        networkBoundResource(
            query = {
                val customer = localCustomerRepo.getLocalCustomer() ?: Customer()
                flowOf(customer)
            },

            fetch = {
                val customer = customerDataSource.retrieve(id)
                customer
            },

            saveFetchResult = {
                localCustomerRepo.saveLocalCustomer(it)
            },
        )


    override suspend fun linkPhoneWithExistingAccount(
        verificationId: String,
        smsCode: String,
        onSuccess: (phone: String) -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        val registrationResource =
            registrationRepo.linkPhoneWithExistingAccount(verificationId, smsCode)
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