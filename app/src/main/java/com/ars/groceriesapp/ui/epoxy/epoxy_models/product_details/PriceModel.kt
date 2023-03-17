package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ProductDetailsPriceItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel
import java.math.BigDecimal

data class PriceModel(
    private val context: Context,
    private val name: String,
    private val unitPrice: String,
    private val price: BigDecimal,
    private val isFavorite: Boolean,
    private val onFavoriteChanged: (isChecked: Boolean, onFinish: () -> Unit) -> Unit
) : ViewBindingKotlinModel<ProductDetailsPriceItemBinding>(
    R.layout.product_details_price_item
) {

    private var currentQuantity = 1


    @SuppressLint("SetTextI18n")
    override fun ProductDetailsPriceItemBinding.bind() {

        productDetailsFavoriteCb.setOnCheckedChangeListener(null)
        productDetailsFavoriteCb.isChecked = isFavorite

        val onQuantityChanged = {
            productDetailsPriceTv.text = getPriceOfQuantity(currentQuantity)
            productDetailsQuantityTv.text = currentQuantity.toString()
        }


        productDetailsIncreaseBtn.setOnClickListener {
            ++currentQuantity
            onQuantityChanged()
        }
        productDetailsDecreaseBtn.setOnClickListener {
            if (currentQuantity > 1) --currentQuantity
            onQuantityChanged()
        }
        productDetailsFavoriteCb.setOnCheckedChangeListener { _, isChecked ->
            onFavoriteChanged(isChecked){}
        }
        productDetailsNameTv.text = name
        productDetailsUnitPriceTv.text = unitPrice
        productDetailsPriceTv.text = "$$price"
        productDetailsQuantityTv.text = currentQuantity.toString()
    }


    private fun getPriceOfQuantity(quantity: Int) =
        "$" + price.times(quantity.toBigDecimal())

}
