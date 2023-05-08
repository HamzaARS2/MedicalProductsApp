package com.ars.data.network

import com.ars.data.extensions.asCustomer
import com.ars.data.extensions.asNetworkCustomer
import com.ars.data.network.api.CustomerApi
import com.ars.domain.model.Customer
import com.ars.domain.repository.ICRUDRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CustomerDataSource @Inject constructor(
    private val customerApi: CustomerApi
) {
    suspend fun insert(data: Customer): Resource<Customer> {
        return try {
            val response = customerApi.insertCustomer(data.asNetworkCustomer()).asCustomer()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    suspend fun retrieve(id: String): Customer? {
        return customerApi.retrieveCustomer(id)?.asCustomer()
    }

    suspend fun retrieveAll(): Resource<List<Customer>> {
        return try {
            val response = customerApi.retrieveAllCustomers().map { it.asCustomer() }
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    fun update(data: Customer) = flow {
        emit(Response.Loading())
        val result = try {
            val response = customerApi.updateCustomer(data).asCustomer()
            Response.Success(response)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable)
        }
        emit(result)
    }

    suspend fun delete(id: String): Resource<String> {
        return try {
            val response = customerApi.deleteCustomer(id)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }


}