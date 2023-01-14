package com.ars.groceriesapp.ui.epoxy

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.Toast
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.carousel
import com.bumptech.glide.Glide
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.PopularCategoryItemBinding
import com.ars.groceriesapp.databinding.ShopHeaderItemBinding
import com.ars.groceriesapp.databinding.ShopProductItemBinding
import com.ars.groceriesapp.databinding.ShopSectionTitleItemBinding
import com.ars.groceriesapp.model.Category
import com.ars.domain.model.Product
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel

class ShopEpoxyController(
    private val context: Context
): EpoxyController() {

    private var products: List<Product>? = null
    private var exclusiveProducts: List<Product>? = null
    private var categories: List<Category>? = null

    override fun buildModels() {
        ShopHeader()
            .id("shop_header")
            .addTo(this)

        ShopSection("Exclusive Offer")
            .id("shop_section_1")
            .addTo(this)

        if (!exclusiveProducts.isNullOrEmpty()) {
            val exclusiveModels = mutableListOf<ShopProduct>()
            exclusiveProducts?.forEachIndexed { index, product ->
                exclusiveModels.add(
                    ShopProduct(product,::onProductAddToCartClick).apply {
                        id(index)
                    }
                )
            }
            carousel {
                id("exclusive_offer_carousel")
                numViewsToShowOnScreen(2F)
                models(exclusiveModels)
            }
        }

        if (!categories.isNullOrEmpty()) {
            ShopSection("Popular Categories")
                .id("shop_section_2")
                .addTo(this)


            val categoriesModels = mutableListOf<ShopCategory>()
            categories?.forEachIndexed { index, category ->
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

        if (!products.isNullOrEmpty()) {
            val productsModels = mutableListOf<ShopProduct>()
            products?.forEachIndexed { index, product ->
                productsModels.add(
                    ShopProduct(product,::onProductAddToCartClick).apply {
                        id(index)
                    }
                )
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

    fun setProducts(products: List<Product>?) {
        this.products = products
        requestModelBuild()
    }

    fun setExclusiveProducts(products: List<Product>?) {
        this.exclusiveProducts = products
        requestModelBuild()
    }

    fun setCategories(categories: List<Category>?) {
        this.categories = categories
        requestModelBuild()
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