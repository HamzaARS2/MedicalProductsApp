package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.content.Context
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ProductDetailsTitleItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel

data class TitleModel(
    private val context: Context
): ViewBindingKotlinModel<ProductDetailsTitleItemBinding>(R.layout.product_details_title_item) {
    override fun ProductDetailsTitleItemBinding.bind() {

    }
}
