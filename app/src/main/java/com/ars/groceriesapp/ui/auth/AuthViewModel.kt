package com.ars.groceriesapp.ui.auth


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.Customer
import com.ars.domain.usercase.CustomerRegistrationUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val customerUseCase: CustomerRegistrationUseCase
) : ViewModel() {

    private val _customerLoginFlow: MutableSharedFlow<Resource<Customer?>> = MutableSharedFlow()
    val customerLoginFlow: SharedFlow<Resource<Customer?>> get() = _customerLoginFlow
    private val _customerRegisterFlow: MutableStateFlow<Resource<Customer?>?> = MutableStateFlow(null)
    val customerRegisterFlow: StateFlow<Resource<Customer?>?> get() = _customerRegisterFlow

    fun register(name: String, email: String, password: String, phone: String, address: String) = viewModelScope.launch {
            _customerRegisterFlow.value = Resource.Loading
            val customer = customerUseCase.registerCustomer(name, email, password, phone, address)
            _customerRegisterFlow.emit(customer)
        }

    fun login(email: String, password: String) = viewModelScope.launch {
        _customerLoginFlow.emit(Resource.Loading)
        val customer = customerUseCase.loginCustomer(email, password)
        _customerLoginFlow.emit(customer)
    }

    fun logout() {
        customerUseCase.logoutCustomer()
    }

    fun isLoggedIn() = customerUseCase.isLoggedIn()


}