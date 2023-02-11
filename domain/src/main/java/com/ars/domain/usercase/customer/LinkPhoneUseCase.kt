package com.ars.domain.usercase.customer

import com.ars.domain.repository.customer.ICustomerRepository
import javax.inject.Inject

class LinkPhoneUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    suspend operator fun invoke(
        verificationId: String, smsCode: String,
        onSuccess: (phone: String) -> Unit,
        onFailure: (e: Exception) -> Unit
    ) =
        customerRepository.linkPhoneWithExistingAccount(
            verificationId,
            smsCode,
            onSuccess,
            onFailure
        )
}