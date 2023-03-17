package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.content.Context
import androidx.core.view.isVisible
import com.ars.domain.model.Review
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ProductDetailsReviewsItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel
import com.ars.groceriesapp.ui.home.product_details.ReviewsAdapter

data class ReviewsModel(
    private val context: Context,
    private val reviewsAdapter: ReviewsAdapter,
    private val reviews: List<Review>?,
    private val rating: Float
): ViewBindingKotlinModel<ProductDetailsReviewsItemBinding>(R.layout.product_details_reviews_item) {
    override fun ProductDetailsReviewsItemBinding.bind() {

        reviewItemRatingRb.rating = rating
        productDetailsReviewsRv.apply {
            adapter = reviewsAdapter
            setHasFixedSize(true)
        }
        reviewsAdapter.differ.submitList(reviews)

        productDetailsReviewsForwardDownCb.setOnCheckedChangeListener { _, isChecked ->
            productDetailsReviewsRv.isVisible = isChecked
        }
    }

}
