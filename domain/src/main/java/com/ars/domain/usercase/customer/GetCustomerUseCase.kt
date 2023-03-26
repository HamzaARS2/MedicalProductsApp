package com.ars.domain.usercase.customer

import com.ars.domain.model.Customer
import com.ars.domain.repository.customer.ICustomerRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCustomerUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    suspend operator fun invoke(id: String): Flow<Response<Customer?>> =
        customerRepository.getCustomer(id)
}