package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.content.Context
import android.view.View
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ProductDetailsOrderItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel

data class OrderModel(
    private val context: Context
): ViewBindingKotlinModel<ProductDetailsOrderItemBinding>(R.layout.product_details_order_item) {
    override fun ProductDetailsOrderItemBinding.bind() {
        productDetailsOrderNowBtn.visibility = View.INVISIBLE
        productDetailsOrderCartBtn.visibility = View.INVISIBLE
    }
}