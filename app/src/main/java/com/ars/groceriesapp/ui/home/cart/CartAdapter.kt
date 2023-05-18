package com.ars.groceriesapp.ui.home.cart

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ars.domain.model.CartItem
import com.ars.groceriesapp.databinding.CartProductItemBinding
import com.bumptech.glide.Glide

class CartAdapter(
    val onItemQuantityChanged: (position: Int, id: Int) -> Unit,
    val onRemoveItemClick: (cartItem: CartItem, onFinish:() -> Unit) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartItemHolder>() {

    private var minQuantity = 1


    private val differCallBack = object : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
            oldItem.customerId == newItem.customerId

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemHolder =
        CartItemHolder(
            CartProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CartItemHolder, position: Int) {
        holder.bindCartItem(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setMinQuantity(quantity: Int) {
        minQuantity = quantity
        notifyDataSetChanged()
    }

    inner class CartItemHolder(private val binding: CartProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                cartItemIncreaseBtn.setOnClickListener {
                   increaseItemQuantity(bindingAdapterPosition)
                }
                cartItemDecreaseBtn.setOnClickListener {
                   decreaseItemQuantity(bindingAdapterPosition)
                }
                cartItemRemoveImb.setOnClickListener {
                    cartItemRemoveImb.visibility = View.INVISIBLE
                    cartItemRemoveBtnProgress.isVisible = true
                    onRemoveItemClick(differ.currentList[bindingAdapterPosition]) {
                        cartItemRemoveImb.isVisible = true
                        cartItemRemoveBtnProgress.isVisible = false
                    }
                }
            }
        }

        private fun increaseItemQuantity(position: Int) {
            val updatedQuantity = differ.currentList[position].quantity + 1
            differ.currentList[position].quantity = updatedQuantity
            onItemQuantityChanged(
                updatedQuantity,
                differ.currentList[bindingAdapterPosition].id!!
            )
            notifyItemChanged(position)

        }

        private fun decreaseItemQuantity(position: Int) {
            var updatedQuantity = differ.currentList[position].quantity - 1
            if (updatedQuantity <= minQuantity) updatedQuantity = minQuantity
            differ.currentList[position].quantity = updatedQuantity
            onItemQuantityChanged(
                updatedQuantity,
                differ.currentList[bindingAdapterPosition].id!!
            )
            notifyItemChanged(position)
        }

        @SuppressLint("SetTextI18n")
        fun bindCartItem(cartItem: CartItem) {
            val product = cartItem.product
            if (product != null) {
                binding.apply {
                    Glide.with(cartItemImageImv).load(product.image).into(cartItemImageImv)
                    cartItemTitleTv.text = product.name
                    cartItemProductKgPcsTv.text = product.priceUnit
                    cartItemQuantityTv.text = cartItem.quantity.toString()
                    cartItemPriceTv.text = "$" + product.price.times(cartItem.quantity.toBigDecimal())
                }
            }
        }

    }
}