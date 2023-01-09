package com.reddevx.groceriesapp.ui.epoxy

import android.graphics.Color
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.carousel
import com.reddevx.groceriesapp.R
import com.reddevx.groceriesapp.databinding.PopularCategoryItemBinding
import com.reddevx.groceriesapp.databinding.ShopHeaderItemBinding
import com.reddevx.groceriesapp.databinding.ShopProductItemBinding
import com.reddevx.groceriesapp.databinding.ShopSectionTitleItemBinding
import com.reddevx.groceriesapp.model.Category
import com.reddevx.groceriesapp.model.Product
import com.reddevx.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel

class ShopEpoxyController: Typed2EpoxyController<List<Product>,List<Category>>() {

    override fun buildModels(products: List<Product>?, categories: List<Category>?) {
        ShopHeader()
            .id("shop_header")
            .addTo(this)

        ShopSection("Exclusive Offer")
            .id("shop_section_1")
            .addTo(this)

        if (products == null) {
            return
        }
        val productsModels = mutableListOf<ShopProduct>()
        products.forEachIndexed { index, p ->
            productsModels.add(
                ShopProduct(p).apply {
                    id(index)
                }
            )
        }
        carousel {
            id("exclusive_offer_carousel")
            numViewsToShowOnScreen(2F)
            models(productsModels)
        }
        if (categories != null) {
            ShopSection("Popular Categories")
                .id("shop_section_2")
                .addTo(this)


            val categoriesModels = mutableListOf<ShopCategory>()
            categories.forEachIndexed { index, category ->
                categoriesModels.add(
                    ShopCategory(category).apply {
                        id(index)
                    }
                )
            }
            carousel {
                id("popular_categories_carousel")
                numViewsToShowOnScreen(1.5F)
                models(categoriesModels)
            }
        }

        ShopSection("On Sale")
            .id("shop_section_3")
            .addTo(this)

        carousel {
            id("on_sale_carousel")
            numViewsToShowOnScreen(2F)
            models(productsModels)
        }

        ShopSection("Most Rated")
            .id("shop_section_4")
            .addTo(this)

        carousel {
            id("most_rated_carousel")
            numViewsToShowOnScreen(2F)
            models(productsModels)
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

data class ShopCategory(
    val category: Category
): ViewBindingKotlinModel<PopularCategoryItemBinding>(R.layout.popular_category_item) {
    override fun PopularCategoryItemBinding.bind() {
        popularCategoryImv.setImageResource(category.image)
        popularCategoryNameTv.text = category.name
        root.setCardBackgroundColor(Color.parseColor(category.color))
    }
}