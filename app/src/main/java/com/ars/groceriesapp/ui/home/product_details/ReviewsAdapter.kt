package com.ars.groceriesapp.ui.home.product_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.domain.model.Category
import com.ars.domain.model.Review
import com.ars.groceriesapp.databinding.ReviewItemBinding

class ReviewsAdapter() : RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsHolder =
        ReviewsHolder(
            ReviewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: ReviewsHolder, position: Int) {
        holder.bindReview(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ReviewsHolder(private val binding: ReviewItemBinding) : ViewHolder(binding.root) {

        init {
            binding.reviewItemForwardDownCb.setOnCheckedChangeListener { _, isChecked ->
                binding.reviewItemDescriptionTv.isVisible = isChecked
            }
        }


        fun bindReview(review: Review) {
            binding.run {
                reviewItemCustomerNameTv.text = review.customer.name
                reviewItemRatingRb.rating = review.rating
                reviewItemDescriptionTv.text = review.comment
            }
        }
    }
}