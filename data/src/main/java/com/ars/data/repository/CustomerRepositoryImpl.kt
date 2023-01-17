package com.ars.data.repository

import com.ars.data.remote.api.CustomerApi
import com.ars.domain.model.Customer
import com.ars.domain.repository.ICustomerRepository
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val customerApi: CustomerApi
): ICustomerRepository {
    override suspend fun insert(data: Customer): Customer {
      return customerApi.insertCustomer(data)
    }

    override suspend fun retrieve(id: String): Customer? {
        return customerApi.retrieveCustomer(id)
    }

    override suspend fun retrieveAll(): List<Customer> {
        return customerApi.retrieveAllCustomers()
    }

    override suspend fun update(id: String, data: Customer): Customer {
        return customerApi.updateCustomer(data)
    }

    override suspend fun delete(id: String): String {
        return customerApi.deleteCustomer(id)
    }
}