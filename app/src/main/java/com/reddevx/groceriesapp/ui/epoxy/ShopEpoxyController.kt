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

        ShopSection("Exclusive Offer")
            .id("shop_section_1")
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
           id("exclusive_offer_carousel")
            numViewsToShowOnScreen(2F)
            models(products)
        }

        ShopSection("On Sale")
            .id("shop_section_2")
            .addTo(this)

        carousel {
            id("on_sale_carousel")
            numViewsToShowOnScreen(2F)
            models(products)
        }


    }
}

class ShopHeader: ViewBindingKotlinModel<ShopHeaderItemBinding>(R.layout.shop_header_item) {
    override fun ShopHeaderItemBinding.bind() {
    }
}

data class ShopSection(
    val sectionTitle: String
): ViewBindingKotlinModel<ShopSectionTitleItemBinding>(R.layout.shop_section_title_item) {
    override fun ShopSectionTitleItemBinding.bind() {
        shopSectionTitleTv.text = sectionTitle
    }
}

data class ShopProduct(
    val product: Product
): ViewBindingKotlinModel<ShopProductItemBinding>(R.layout.shop_product_item) {
    override fun ShopProductItemBinding.bind() {
    }

}