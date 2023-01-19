package com.ars.domain.usercase

import com.ars.domain.repository.ICustomerLoginState
import javax.inject.Inject

class CustomerLoginStateUseCase @Inject constructor(
    private val customerLoginState: ICustomerLoginState
) {

    fun isLoggedIn() = customerLoginState.isLoggedIn()

    fun getCustomerDocId() = customerLoginState.currentUser?.uid
}