package com.ars.groceriesapp.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.Customer
import com.ars.domain.usercase.customer.RegisterCustomerUseCase
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerCustomerUseCase: RegisterCustomerUseCase
) : ViewModel() {

    private val _customerRegisterFlow: MutableStateFlow<Resource<Customer>?> =
        MutableStateFlow(null)
    val customerRegisterFlow: StateFlow<Resource<Customer>?> get() = _customerRegisterFlow.asStateFlow()


    fun register(
        username: String, email: String, password: String,
        onValidation: (response: Validation.RegisterResponse) -> Unit
    ) = viewModelScope.launch {

        _customerRegisterFlow.value = Resource.Loading
        val response = registerCustomerUseCase(username, email, password, onValidation)
        _customerRegisterFlow.emit(response)

    }



}