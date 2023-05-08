package com.ars.groceriesapp.ui.home.account.customer_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ars.domain.model.Customer
import com.ars.domain.usercase.customer.GetCustomerUseCase
import com.ars.domain.usercase.customer.UpdateCustomerUseCase
import com.ars.domain.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomerInfoViewModel @Inject constructor(
    private val getCustomerUseCase: GetCustomerUseCase,
    private val updateCustomerUseCase: UpdateCustomerUseCase
) : ViewModel() {

    fun updateCustomerChanges(customer: Customer) = updateCustomerUseCase(customer).asLiveData()

}