package com.ars.groceriesapp.ui.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.Customer
import com.ars.domain.usercase.CustomerRegistrationUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val customerUseCase: CustomerRegistrationUseCase
) : ViewModel() {

    private val _customerState: MutableStateFlow<Resource<Customer?>?> = MutableStateFlow(null)
    val customerState: StateFlow<Resource<Customer?>?> get() = _customerState
    private val _loginState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> get() = _loginState


    init {
        listenToUserLogin()
    }

    fun register(name: String, email: String, password: String, phone: String, address: String) = viewModelScope.launch {
        _customerState.value = Resource.Loading
        val customer = customerUseCase.registerCustomer(name, email, password, phone, address)
        _customerState.emit(customer)

    }

    fun login(email: String,password: String) = viewModelScope.launch {
        _customerState.value = Resource.Loading
        val customer = customerUseCase.loginCustomer(email, password)
        _customerState.emit(customer)
    }

    fun logout() {
        customerUseCase.logoutCustomer()
    }

    private fun listenToUserLogin() {
        customerUseCase.setLoginStateListener {
            _loginState.value = it
        }
    }





}