package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.content.Context
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ProductDetailsImageItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel
import com.bumptech.glide.Glide

data class HeaderImageModel(
    private val context: Context,
    private val imageUrl: String?,
    private val onBackClicked: () -> Unit,
    private val onAddToCartClicked: () -> Unit
) : ViewBindingKotlinModel<ProductDetailsImageItemBinding>(R.layout.product_details_image_item) {
    override fun ProductDetailsImageItemBinding.bind() {
        imageUrl?.let {
            Glide.with(productDetailsImageImv).load(it).into(productDetailsImageImv)
        }
        productDetailsBackBtn.setOnClickListener {
            onBackClicked()
        }

        productDetailsCartBtn.setOnClickListener {
            onAddToCartClicked()
        }
    }
}
