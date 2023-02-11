package com.ars.domain.usercase.customer

import com.ars.domain.model.Customer
import com.ars.domain.repository.customer.ICustomerRepository
import com.ars.domain.utils.Resource
import javax.inject.Inject

class UpdateCustomerUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    suspend operator fun invoke(customer: Customer): Resource<Customer> {
        return customerRepository.update(customer)
    }
}