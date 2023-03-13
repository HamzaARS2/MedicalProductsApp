package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ProductDetailsPriceItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel

data class PriceModel(
    private val context: Context,
    private val name: String,
    private val unitPrice: String,
    private val price: String,
    private val onFavoriteChanged: (isChecked: Boolean, onFinish: () -> Unit) -> Unit
) : ViewBindingKotlinModel<ProductDetailsPriceItemBinding>(
    R.layout.product_details_price_item
) {
    @SuppressLint("SetTextI18n")
    override fun ProductDetailsPriceItemBinding.bind() {
        productDetailsFavoriteCb.setOnCheckedChangeListener { _, isChecked ->
            productDetailsFavoriteCb.isEnabled = false
            onFavoriteChanged(isChecked) {
                // Operation finished
                productDetailsFavoriteCb.isEnabled = true
            }
            productDetailsFavoriteCb.buttonTintList = if (isChecked)  ColorStateList.valueOf(Color.RED)
            else  ColorStateList.valueOf(Color.parseColor("#7C7C7C"))
        }
        productDetailsNameTv.text = name
        productDetailsUnitPriceTv.text = unitPrice
        productDetailsPriceTv.text = "$$price"
    }
}
