package com.ars.groceriesapp.ui.auth.phone_location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.auth.RegistrationRepository
import com.ars.domain.model.Customer
import com.ars.domain.usercase.customer.LinkPhoneUseCase
import com.ars.domain.usercase.customer.UpdateCustomerUseCase
import com.ars.domain.utils.Resource
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneLocationViewModel @Inject constructor(
    private val linkPhoneUseCase: LinkPhoneUseCase,
    private val updateCustomerUseCase: UpdateCustomerUseCase
) : ViewModel() {

    var customer = Customer()
    var verificationId = ""
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private val _updatedCustomer: MutableSharedFlow<Resource<Customer>> = MutableSharedFlow()
    val updatedCustomer: SharedFlow<Resource<Customer>> get() = _updatedCustomer

    fun linkPhoneWithCustomerAccount(
        verificationId: String, smsCode: String, onSuccess: (phone: String) -> Unit,
        onFailure: (e: Exception) -> Unit
    ) = viewModelScope.launch {
        linkPhoneUseCase(verificationId, smsCode, onSuccess, onFailure)
    }

    fun updateCustomer() = viewModelScope.launch {
        _updatedCustomer.tryEmit(Resource.Loading)
        val updatedCustomer = updateCustomerUseCase(customer)
        _updatedCustomer.emit(updatedCustomer)
    }

}