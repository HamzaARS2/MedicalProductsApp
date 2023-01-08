package com.reddevx.groceriesapp.ui.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.reddevx.groceriesapp.R
import com.reddevx.groceriesapp.databinding.ShopHeaderItemBinding
import com.reddevx.groceriesapp.databinding.ShopProductItemBinding
import com.reddevx.groceriesapp.databinding.ShopSectionTitleItemBinding
import com.reddevx.groceriesapp.model.Product
import com.reddevx.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel

class ShopEpoxyController: TypedEpoxyController<List<Product>>() {
    override fun buildModels(data: List<Product>?) {

        ShopHeader()
            .id("shop_header")
            .addTo(this)

        ShopSection()
            .id("shop_section")
            .addTo(this)

        if (data == null) {
            return
        }
        val products = mutableListOf<ShopProduct>()
        data.forEachIndexed { index, p ->
            products.add(
                ShopProduct(p).apply {
                    id(index)
                }
            )
        }
        carousel {
           id("carousel")
            numViewsToShowOnScreen(6F)
            models(products)
        }


    }
}

class ShopHeader: ViewBindingKotlinModel<ShopHeaderItemBinding>(R.layout.shop_header_item) {
    override fun ShopHeaderItemBinding.bind() {
    }
}

class ShopSection: ViewBindingKotlinModel<ShopSectionTitleItemBinding>(R.layout.shop_section_title_item) {
    override fun ShopSectionTitleItemBinding.bind() {
    }
}

data class ShopProduct(
    val product: Product
): ViewBindingKotlinModel<ShopProductItemBinding>(R.layout.shop_product_item) {
    override fun ShopProductItemBinding.bind() {
    }

}