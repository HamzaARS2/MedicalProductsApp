package com.ars.groceriesapp.ui.home.order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ars.data.repository.OrderRepository
import com.ars.domain.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    fun placeOrder(order: Order) =
        orderRepository.placeNewOrder(order)
            .asLiveData()

}