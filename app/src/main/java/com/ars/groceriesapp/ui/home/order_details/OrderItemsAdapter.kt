package com.ars.groceriesapp.ui.home.order_details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.domain.model.OrderItem
import com.ars.groceriesapp.databinding.OrderDetailsItemBinding
import com.bumptech.glide.Glide

class OrderItemsAdapter(private val items: List<OrderItem>): RecyclerView.Adapter<OrderItemsAdapter.OrderItemsHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsHolder =
        OrderItemsHolder(
            OrderDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )



    override fun onBindViewHolder(holder: OrderItemsHolder, position: Int) {
        holder.bindItem(items[position])
    }


    override fun getItemCount(): Int = items.size






    inner class OrderItemsHolder(private val binding: OrderDetailsItemBinding): ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindItem(item: OrderItem) {
                binding.apply {
                    Glide.with(root).load(item.productImage).into(orderItemImageImv)
                    orderItemProductNameTv.text = item.productName
                    orderItemUnitPriceTv.text = item.productUnitPrice
                    orderItemQuantityTv.text = "x${item.quantity}"
                    orderItemPriceTv.text = "$" + item.subTotalPrice
                }

        }
    }














}