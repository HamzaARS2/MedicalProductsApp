package com.ars.groceriesapp.ui.home.cart

import android.annotation.SuppressLint
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
    val onIncreaseQuantityClick: (position: Int) -> Unit,
    val onDecreaseQuantityClick: (position: Int) -> Unit,
    val onRemoveItemClick: (cartItem: CartItem, onFinish:() -> Unit) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartItemHolder>() {


    private val differCallBack = object : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
            oldItem.id == newItem.id

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

    fun updateCartItem(cartItem: CartItem, position: Int) {
        differ.currentList[position].quantity = cartItem.quantity
        notifyItemChanged(position)
    }

    inner class CartItemHolder(private val binding: CartProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                cartItemIncreaseBtn.setOnClickListener {
                    onIncreaseQuantityClick(
                        bindingAdapterPosition
                    )
                }
                cartItemDecreaseBtn.setOnClickListener {
                    onDecreaseQuantityClick(
                        bindingAdapterPosition
                    )
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

        @SuppressLint("SetTextI18n")
        fun bindCartItem(cartItem: CartItem) {
            val product = cartItem.product
            if (product != null)
            binding.apply {
                Glide.with(cartItemImageImv).load(product.image).into(cartItemImageImv)
                cartItemTitleTv.text = product.name
                cartItemProductKgPcsTv.text = product.priceUnit
                cartItemQuantityTv.text = cartItem.quantity.toString()
                cartItemPriceTv.text = "$" + product.price
            }
        }

    }
}