package com.ars.groceriesapp.ui.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.Customer
import com.ars.domain.usercase.AppLaunchUseCase
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

    private val _customerState: MutableSharedFlow<Resource<Customer?>> = MutableSharedFlow()
    val customerState: SharedFlow<Resource<Customer?>?> get() = _customerState

    fun register(name: String, email: String, password: String, phone: String, address: String) =
        viewModelScope.launch {
            _customerState.emit(Resource.Loading)
            val customer = customerUseCase.registerCustomer(name, email, password, phone, address)
            _customerState.emit(customer)

        }

    fun login(email: String, password: String) = viewModelScope.launch {
        _customerState.emit(Resource.Loading)
        val customer = customerUseCase.loginCustomer(email, password)
        _customerState.emit(customer)
    }

    fun logout() {
        customerUseCase.logoutCustomer()
    }

    fun isLoggedIn() = customerUseCase.isLoggedIn()


}