package com.ars.groceriesapp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.Customer
import com.ars.domain.usercase.customer.LoginCustomerUseCase
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginCustomerUseCase: LoginCustomerUseCase
): ViewModel() {

    private val _customerLoginFlow: MutableSharedFlow<Resource<Customer>?> = MutableSharedFlow()
    val customerLoginFlow: SharedFlow<Resource<Customer>?> get() = _customerLoginFlow

    fun login(email: String, password: String,onValidation: (response: Validation.LoginResponse) -> Unit) = viewModelScope.launch {
        _customerLoginFlow.tryEmit(Resource.Loading)
        val customer = loginCustomerUseCase(email, password,onValidation)
        _customerLoginFlow.emit(customer)
    }

}