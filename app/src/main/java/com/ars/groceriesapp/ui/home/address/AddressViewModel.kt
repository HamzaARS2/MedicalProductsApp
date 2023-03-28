package com.ars.groceriesapp.ui.home.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.AddressRepository
import com.ars.domain.model.Address
import com.ars.domain.model.Customer
import com.ars.domain.usercase.customer.UpdateCustomerUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
): ViewModel() {

    private val _updatedCustomerFlow: MutableStateFlow<Resource<Customer?>?> = MutableStateFlow(null)
    val updatedCustomerFlow: StateFlow<Resource<Customer?>?> get() = _updatedCustomerFlow

    fun saveAddress(address: Address, customerId: String) =
        addressRepository.saveAddress(address, customerId).asLiveData()

}