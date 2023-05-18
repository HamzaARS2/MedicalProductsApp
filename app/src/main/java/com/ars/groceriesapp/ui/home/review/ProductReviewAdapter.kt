package com.ars.groceriesapp.ui.home.review

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.domain.model.Product
import com.ars.domain.model.ProductReview
import com.ars.groceriesapp.databinding.AddReviewProductItemBinding
import com.bumptech.glide.Glide

class ProductReviewAdapter(private val onProductClick: (productReview: ProductReview) -> Unit): RecyclerView.Adapter<ProductReviewAdapter.ProductReviewHolder>() {


    private val differCallBack = object : DiffUtil.ItemCallback<ProductReview>() {
        override fun areItemsTheSame(oldItem: ProductReview, newItem: ProductReview): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductReview, newItem: ProductReview): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallBack)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductReviewHolder =
        ProductReviewHolder(
            AddReviewProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProductReviewHolder, position: Int) {
        holder.bindProduct(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ProductReviewHolder(private val binding: AddReviewProductItemBinding): ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener {
                onProductClick(differ.currentList[bindingAdapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bindProduct(productReview: ProductReview?) {
            productReview ?: return
            binding.apply {
                Glide.with(addReviewProductImageImv).load(productReview.image).into(addReviewProductImageImv)
                addReviewProductNameTv.text = productReview.name
                addReviewProductUnitPriceTv.text = productReview.priceUnit
                addReviewProductPriceTv.text = "$${productReview.price}"
                addReviewProductReviewedTv.isVisible = productReview.reviewed
            }
        }

    }
}