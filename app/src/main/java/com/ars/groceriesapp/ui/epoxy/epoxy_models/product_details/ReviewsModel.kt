package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.content.Context
import androidx.core.view.isVisible
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ProductDetailsReviewsItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel
import com.ars.groceriesapp.ui.home.product_details.ReviewsAdapter

data class ReviewsModel(
    private val context: Context,
    private val reviewsAdapter: ReviewsAdapter
): ViewBindingKotlinModel<ProductDetailsReviewsItemBinding>(R.layout.product_details_reviews_item) {
    override fun ProductDetailsReviewsItemBinding.bind() {
        productDetailsReviewsRv.apply {
            adapter = reviewsAdapter
            setHasFixedSize(true)
        }

        productDetailsReviewsForwardDownCb.setOnCheckedChangeListener { _, isChecked ->
            productDetailsReviewsRv.isVisible = isChecked
        }
    }

}
