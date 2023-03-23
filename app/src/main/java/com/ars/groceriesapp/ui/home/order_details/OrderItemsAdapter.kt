package com.ars.groceriesapp.ui.home.order_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.groceriesapp.databinding.OrderDetailsItemBinding

class OrderItemsAdapter: RecyclerView.Adapter<OrderItemsAdapter.OrderItemsHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsHolder =
        OrderItemsHolder(
            OrderDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )



    override fun onBindViewHolder(holder: OrderItemsHolder, position: Int) {

    }


    override fun getItemCount(): Int = 7






    inner class OrderItemsHolder(private val binding: OrderDetailsItemBinding): ViewHolder(binding.root) {

    }














}