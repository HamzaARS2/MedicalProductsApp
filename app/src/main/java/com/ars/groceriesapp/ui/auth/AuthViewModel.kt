package com.ars.groceriesapp.ui.auth


import android.app.Application
import androidx.lifecycle.*
import com.ars.domain.model.Customer
import com.ars.domain.usercase.customer.LoginCustomerUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginCustomerUseCase: LoginCustomerUseCase,
    application: Application
) : AndroidViewModel(application) {

    var customer = Customer()

    private val _customerLoginFlow: MutableSharedFlow<Resource<Customer?>> = MutableSharedFlow()
    val customerLoginFlow: SharedFlow<Resource<Customer?>> get() = _customerLoginFlow
    private val _customerRegisterFlow: MutableStateFlow<Resource<Customer?>?> =
        MutableStateFlow(null)
    val customerRegisterFlow: StateFlow<Resource<Customer?>?> get() = _customerRegisterFlow

//    fun register(name: String, email: String, password: String, phone: String, address: String) =
//        viewModelScope.launch {
//            loginCustomerUseCase
//            _customerRegisterFlow.value = Resource.Loading
//            val customer = customerUseCase.registerCustomer(name, email, password, phone, address)
//            _customerRegisterFlow.emit(customer)
//        }
//
//    fun getUsers(): Flow<Resource<List<Customer>>> = flow {  }
//
//    fun login(email: String, password: String) = viewModelScope.launch {
//        _customerLoginFlow.emit(Resource.Loading)
//        val customer = customerUseCase.loginCustomer(email, password)
//        _customerLoginFlow.emit(customer)
//    }
//
//    fun updateCustomer() = viewModelScope.launch {
//        customerUseCase.updateCustomer(customer)
//    }
//
//    fun logout() {
//        customerUseCase.logoutCustomer()
//    }
//
//    fun isLoggedIn() = customerUseCase.isLoggedIn()
}