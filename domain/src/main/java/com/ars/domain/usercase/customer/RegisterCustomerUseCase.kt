package com.ars.domain.usercase.customer

import com.ars.domain.model.Customer
import com.ars.domain.repository.customer.ICustomerRepository
import com.ars.domain.utils.Resource
import javax.inject.Inject

class RegisterCustomerUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    suspend operator fun invoke(
        username: String,
        email: String,
        password: String
    ): Resource<Customer> =
        customerRepository.register(username, email, password)
}