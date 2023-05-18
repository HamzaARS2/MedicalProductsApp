package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ProductDetailsPriceItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel
import com.ars.groceriesapp.ui.home.HomeViewModel
import java.math.BigDecimal
import kotlin.math.min

data class PriceModel(
    private val context: Context,
    private val homeViewModel: HomeViewModel,
    private val lifecycle: LifecycleOwner,
    private val name: String,
    private val unitPrice: String,
    private val price: BigDecimal,
    private val isFavorite: Boolean,
    private val onFavoriteChanged: (isChecked: Boolean, onFinish: () -> Unit) -> Unit,
    private val onProductQuantityChanged: (quantity: Int) -> Unit
) : ViewBindingKotlinModel<ProductDetailsPriceItemBinding>(
    R.layout.product_details_price_item
) {

    private var minQuantity = 1
    private var currentQuantity = 1

    @SuppressLint("SetTextI18n")
    override fun ProductDetailsPriceItemBinding.bind() {

        productDetailsNameTv.text = name
        productDetailsUnitPriceTv.text = unitPrice
        productDetailsPriceTv.text = "$$price"
        productDetailsQuantityTv.text = currentQuantity.toString()

        productDetailsFavoriteCb.setOnCheckedChangeListener(null)
        productDetailsFavoriteCb.isChecked = isFavorite

        val onQuantityChanged = {
            updateQuantity(this)
        }


        homeViewModel.wholesaleMode.observe(lifecycle) { isActive ->
            minQuantity = if (isActive) 10 else 1
            currentQuantity = minQuantity
            onQuantityChanged()
        }


        productDetailsIncreaseBtn.setOnClickListener {
            ++currentQuantity
            onQuantityChanged()
        }
        productDetailsDecreaseBtn.setOnClickListener {
            if (currentQuantity > minQuantity) --currentQuantity
            onQuantityChanged()
        }
        productDetailsFavoriteCb.setOnCheckedChangeListener { _, isChecked ->
            onFavoriteChanged(isChecked) {}
        }

        
    }

    private fun updateQuantity(binding: ProductDetailsPriceItemBinding) {
        binding.productDetailsQuantityTv.text = currentQuantity.toString()
        binding.productDetailsPriceTv.text = getPriceOfQuantity(currentQuantity)
        onProductQuantityChanged(currentQuantity)
    }

    private fun getPriceOfQuantity(quantity: Int) =
        "$" + price.times(quantity.toBigDecimal())

}
