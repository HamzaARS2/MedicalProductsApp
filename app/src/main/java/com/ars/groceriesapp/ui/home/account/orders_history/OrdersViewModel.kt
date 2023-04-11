package com.ars.groceriesapp.ui.home.account.orders_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ars.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderRepo: OrderRepository
) : ViewModel() {


    fun getOrdersLiveData(customerId: String) =
        orderRepo.getOrders(customerId).asLiveData()
}