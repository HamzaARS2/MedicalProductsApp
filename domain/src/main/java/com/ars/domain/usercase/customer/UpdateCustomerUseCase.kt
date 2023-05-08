package com.ars.domain.usercase.customer

import com.ars.domain.model.Customer
import com.ars.domain.repository.customer.ICustomerRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCustomerUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    operator fun invoke(customer: Customer): Flow<Response<Customer>> {
        return customerRepository.update(customer)
    }
}