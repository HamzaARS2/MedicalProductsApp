package com.ars.domain.usercase.customer

import com.ars.domain.model.Customer
import com.ars.domain.repository.customer.ICustomerRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Validation
import javax.inject.Inject

class RegisterCustomerUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        onValidation: (response: Validation.RegisterResponse) -> Unit
    ): Resource<Customer> {
        val response = Validation.registerValidation(username, email, password)
        onValidation(response)
        if (!response.isValidUsername || !response.isValidEmail || !response.isValidPassword)
            return Resource.Loading
        return customerRepository.register(username, email, password)
    }
}