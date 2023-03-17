package com.ars.groceriesapp.ui.home.favorites

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ars.domain.model.FavoriteProduct
import com.ars.groceriesapp.databinding.FavoriteProductItemBinding
import com.bumptech.glide.Glide

class FavoritesAdapter(
    val deleteOnSwipe: DeleteOnSwipe,
    val onFavoriteProductClick: (favoriteProduct: FavoriteProduct) -> Unit,
    val onDeleteFavoriteProduct: (favoriteProduct: FavoriteProduct, onFinish: () -> Unit) -> Unit
) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesHolder>() {


    private val differCallBack = object : DiffUtil.ItemCallback<FavoriteProduct>() {
        override fun areItemsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FavoriteProduct,
            newItem: FavoriteProduct
        ): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesHolder =
        FavoritesHolder(
            FavoriteProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavoritesHolder, position: Int) {
        holder.bindFavoriteProduct(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class FavoritesHolder(private val binding: FavoriteProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener {
                onFavoriteProductClick(differ.currentList[bindingAdapterPosition])
            }

            binding.favoriteItemRemoveImb.setOnClickListener {
                binding.apply {
                    favoriteItemDeleteProgress.visibility = View.VISIBLE
                    favoriteItemRemoveImb.visibility = View.INVISIBLE
                }
                onDeleteFavoriteProduct(differ.currentList[bindingAdapterPosition]) {
                    binding.apply {
                        favoriteItemDeleteProgress.visibility = View.GONE
                        favoriteItemRemoveImb.visibility = View.VISIBLE
                    }
                }
            }

        }

        @SuppressLint("SetTextI18n")
        fun bindFavoriteProduct(favoriteProduct: FavoriteProduct) {
            binding.apply {
                val product = favoriteProduct.product
                Glide.with(favoriteItemImageImv).load(product.image).into(favoriteItemImageImv)
                favoriteItemTitleTv.text = product.name
                favoriteItemProductKgPcsTv.text = product.priceUnit
                favoriteItemPriceTv.text = "$" + product.price
            }
        }
    }
}