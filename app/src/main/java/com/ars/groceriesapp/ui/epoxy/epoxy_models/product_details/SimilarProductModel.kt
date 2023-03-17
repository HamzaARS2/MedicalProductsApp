package com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.ars.domain.model.Product
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ShopProductItemBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel
import com.bumptech.glide.Glide

data class SimilarProductModel(
    private val context: Context,
    private val product: Product?,
    private val onProductAddToCartClick: (productId: Int, onFinish:() -> Unit) -> Unit,
    private val onProductClick: (product: Product) -> Unit
): ViewBindingKotlinModel<ShopProductItemBinding>(R.layout.shop_product_item) {
    @SuppressLint("SetTextI18n")
    override fun ShopProductItemBinding.bind() {
        if (product != null) {
            shopProductProgress.visibility = View.INVISIBLE
            shopProductItemViewsGroup.visibility = View.VISIBLE
            shopProductNameTv.text = product.name
            shopProductKgPcsTv.text = product.priceUnit
            shopProductPriceTv.text = "$${product.price}"
            Glide.with(shopProductImageImv).load(product.image).into(shopProductImageImv)
            shopProductAddBtn.setOnClickListener {
                shopProductAddBtn.visibility = View.INVISIBLE
                shopProductAddBtnProgress.isVisible = true
                onProductAddToCartClick(product.id) {
                    shopProductAddBtn.isVisible = true
                    shopProductAddBtnProgress.isVisible = false
                }
            }
            rootCv.setOnClickListener {
                onProductClick(product)
            }
        } else {
            shopProductProgress.visibility = View.VISIBLE
            shopProductItemViewsGroup.visibility = View.INVISIBLE
        }
    }
}