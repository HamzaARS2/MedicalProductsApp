package com.ars.groceriesapp.ui.home.account.orders_history

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.domain.model.Order
import com.ars.groceriesapp.databinding.OrderItemBinding
import com.ars.groceriesapp.utils.ACTIVE_ORDERS
import com.ars.groceriesapp.utils.DELIVERED_ORDERS
import com.ars.groceriesapp.utils.STATUS_DELIVERED

class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.OrdersHolder>() {

    private var allOrders: List<Order>? = emptyList()

    private val onSectionChanged = { section: Int ->
       val filteredList =  if (section == ACTIVE_ORDERS) {
            allOrders?.filter { it.status != STATUS_DELIVERED }
        } else {
            allOrders?.filter { it.status == STATUS_DELIVERED }
       }

        differ.submitList(filteredList)
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersHolder =
        OrdersHolder(
            OrderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: OrdersHolder, position: Int) {
        holder.bindOrder(differ.currentList[position])
    }

    override fun getItemCount(): Int =
        differ.currentList.size

    fun setData(data: List<Order>?) {
        allOrders = data
        setOnSectionChanged(ACTIVE_ORDERS)
    }

    fun setOnSectionChanged(section: Int) {
        onSectionChanged(section)
    }


    inner class OrdersHolder(private val binding: OrderItemBinding) : ViewHolder(binding.root) {

        init {
            binding.orderItemTrackOrderBtn.setOnClickListener {
                Log.d("TrackBtn", "TrackBtn:  Clicked")
            }
        }

        @SuppressLint("SetTextI18n")
        fun bindOrder(order: Order?) {
            order ?: return
            binding.apply {
                orderItemDateTv.text = order.createdAt
                orderItemStatusTv.text = order.status
                orderItemPaymentMethodTv.text = order.paymentMethod
                orderItemTotalPriceTv.text = "$${order.totalPrice}"
                orderItemTrackNumberTv.text = order.trackNumber
            }
        }


    }
}