package com.reddevx.groceriesapp.ui.epoxy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.carousel
import com.bumptech.glide.Glide
import com.reddevx.groceriesapp.R
import com.reddevx.groceriesapp.databinding.PopularCategoryItemBinding
import com.reddevx.groceriesapp.databinding.ShopHeaderItemBinding
import com.reddevx.groceriesapp.databinding.ShopProductItemBinding
import com.reddevx.groceriesapp.databinding.ShopSectionTitleItemBinding
import com.reddevx.groceriesapp.model.Category
import com.reddevx.groceriesapp.model.Product
import com.reddevx.groceriesapp.ui.MainActivity
import com.reddevx.groceriesapp.ui.TAG
import com.reddevx.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel

class ShopEpoxyController(
    private val context: Context
): Typed2EpoxyController<List<Product>,List<Category>>() {

    override fun buildModels(products: List<Product>?, categories: List<Category>?) {
        ShopHeader()
            .id("shop_header")
            .addTo(this)

        ShopSection("Exclusive Offer")
            .id("shop_section_1")
            .addTo(this)

        if (products.isNullOrEmpty()) {
            return
        }

        val exclusiveProducts = products.filter { product -> product.isExclusive }

        val exclusiveModels = mutableListOf<ShopProduct>()
        val productsModels = mutableListOf<ShopProduct>()

        exclusiveProducts.forEachIndexed { index, product ->
            exclusiveModels.add(
                ShopProduct(product,::onProductAddToCartClick).apply {
                    id(index)
                }
            )
        }

        products.forEachIndexed { index, product ->
            productsModels.add(
                ShopProduct(product,::onProductAddToCartClick).apply {
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
                    ShopCategory(category,::onCategoryClick).apply {
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

    private fun onCategoryClick(name: String) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
    }

    private fun onProductAddToCartClick(name: String) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
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
    val product: Product,
    val onProductAddToCartClick: (name: String) -> Unit
): ViewBindingKotlinModel<ShopProductItemBinding>(R.layout.shop_product_item) {
    @SuppressLint("SetTextI18n")
    override fun ShopProductItemBinding.bind() {
        shopProductNameTv.text = product.name
        shopProductKgPcsTv.text = product.priceUnit
        shopProductPriceTv.text = "$${product.price}"
        Glide.with(shopProductImageImv).load(product.image).into(shopProductImageImv)
        shopProductAddBtn.setOnClickListener {
            onProductAddToCartClick("${product.name}\nAdded to cart")
        }
    }
}

data class ShopCategory(
    val category: Category,
    val onCategoryClick: (name: String) -> Unit
): ViewBindingKotlinModel<PopularCategoryItemBinding>(R.layout.popular_category_item) {
    override fun PopularCategoryItemBinding.bind() {
        popularCategoryImv.setImageResource(category.image)
        popularCategoryNameTv.text = category.name
        root.setCardBackgroundColor(Color.parseColor(category.color))
        root.setOnClickListener {
            onCategoryClick(category.name)
        }
    }
}