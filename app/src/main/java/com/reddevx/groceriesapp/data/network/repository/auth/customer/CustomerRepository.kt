package com.reddevx.groceriesapp.data.network.repository.auth.customer

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.reddevx.groceriesapp.api.CustomerService
import com.reddevx.groceriesapp.utils.toCustomer
import com.reddevx.groceriesapp.model.Customer
import com.reddevx.groceriesapp.data.network.Resource
import com.reddevx.groceriesapp.data.network.repository.auth.IAuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val service: CustomerService,
    private val mAuth: FirebaseAuth
): IAuthRepository {
    override val currentUser: FirebaseUser?
        get() = mAuth.currentUser

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Resource<Customer> {
        return try {
            val result = mAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!!
            val customer = insert(user.toCustomer(name))
            Resource.Success(customer)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Resource<Customer?> {
        return try {
            val result = mAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user!!
            val customer = retrieve(user.uid)
            Resource.Success(customer)
        } catch (e: Exception) {
            logout()
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        mAuth.signOut()
    }

    override suspend fun insert(data: Customer): Customer {
        return service.insertCustomer(data)
    }

    override suspend fun retrieve(id: String): Customer? {
        return service.retrieveCustomer(id)
    }

    override suspend fun retrieveAll(): List<Customer> {
        return service.retrieveAllCustomers()
    }

    override suspend fun update(id: String, data: Customer): Customer {
        return service.updateCustomer(data)
    }

    override suspend fun delete(id: String): String {
       return service.deleteCustomer(id)
    }


}