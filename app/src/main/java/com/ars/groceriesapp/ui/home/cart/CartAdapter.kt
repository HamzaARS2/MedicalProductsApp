package com.ars.groceriesapp.ui.home.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ars.domain.model.CartItem
import com.ars.groceriesapp.databinding.CartProductItemBinding
import com.bumptech.glide.Glide

class CartAdapter(
    private val items: List<CartItem>,
    val onIncreaseQuantityClick: (position: Int) -> Unit,
    val onDecreaseQuantityClick: (position: Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemHolder =
        CartItemHolder(
            CartProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CartItemHolder, position: Int) {
        holder.bindCartItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateCartItem(cartItem: CartItem, position: Int) {
        items[position].quantity = cartItem.quantity
         notifyItemChanged(position)
    }

    inner class CartItemHolder(private val binding: CartProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                cartItemIncreaseBtn.setOnClickListener { onIncreaseQuantityClick(bindingAdapterPosition) }
                cartItemDecreaseBtn.setOnClickListener { onDecreaseQuantityClick(bindingAdapterPosition) }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bindCartItem(cartItem: CartItem) {
            val product = cartItem.product
            binding.apply {
                Glide.with(cartItemImageImv).load(product.image).into(cartItemImageImv)
                cartItemTitleTv.text = product.name
                cartItemProductKgPcsTv.text = product.priceUnit
                cartItemQuantityTv.text = cartItem.quantity.toString()
                cartItemPriceTv.text = "$" + (product.price).times(cartItem.quantity.toBigDecimal())
            }
        }

    }
}