package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.content.Context
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ShopProductItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel

data class SimilarProductModel(
    private val context: Context
): ViewBindingKotlinModel<ShopProductItemBinding>(R.layout.shop_product_item) {
    override fun ShopProductItemBinding.bind() {

    }
}