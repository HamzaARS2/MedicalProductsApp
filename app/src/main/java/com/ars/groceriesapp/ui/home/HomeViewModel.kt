package com.ars.groceriesapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ars.domain.model.Customer

class HomeViewModel : ViewModel() {

    private val mCustomer: MutableLiveData<Customer?> = MutableLiveData(null)

    private val _wholesaleMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val wholesaleMode: LiveData<Boolean> get() = _wholesaleMode

    fun setWholesaleMode(value: Boolean) {
        _wholesaleMode.value = value
    }

    fun setCustomer(customer: Customer?) {
        mCustomer.value = customer
    }

    fun getCustomerLiveData(): LiveData<Customer?> =
        mCustomer

    fun getCustomer(): Customer? =
        mCustomer.value
}