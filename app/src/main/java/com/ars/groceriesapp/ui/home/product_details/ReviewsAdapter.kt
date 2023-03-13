package com.ars.groceriesapp.ui.home.product_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.groceriesapp.databinding.ReviewItemBinding

class ReviewsAdapter() : RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsHolder =
        ReviewsHolder(
            ReviewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: ReviewsHolder, position: Int) {
        holder.bindReview()
    }

    override fun getItemCount(): Int = 3

    inner class ReviewsHolder(private val binding: ReviewItemBinding) : ViewHolder(binding.root) {

        init {
            binding.reviewItemForwardDownCb.setOnCheckedChangeListener { _, isChecked ->
                binding.reviewItemDescriptionTv.isVisible = isChecked
            }
        }


        fun bindReview() {

        }
    }
}