package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.content.Context
import androidx.core.view.isVisible
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ProductDetailsDescriptionItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel

data class DescriptionModel(
    private val context: Context,
    private val description: String
) : ViewBindingKotlinModel<ProductDetailsDescriptionItemBinding>(R.layout.product_details_description_item) {
    override fun ProductDetailsDescriptionItemBinding.bind() {
        descriptionTv.text = description
        productDetailsForwardDownCb.setOnCheckedChangeListener { _, isChecked ->
            descriptionTv.isVisible = isChecked
        }
    }
}
