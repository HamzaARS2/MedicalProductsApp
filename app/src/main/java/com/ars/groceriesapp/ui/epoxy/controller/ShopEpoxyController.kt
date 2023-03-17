package com.ars.groceriesapp.ui.epoxy.controller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.carousel
import com.bumptech.glide.Glide
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.PopularCategoryItemBinding
import com.ars.groceriesapp.databinding.ShopHeaderItemBinding
import com.ars.groceriesapp.databinding.ShopProductItemBinding
import com.ars.groceriesapp.databinding.ShopSectionTitleItemBinding
import com.ars.domain.model.Category
import com.ars.domain.model.Customer
import com.ars.domain.model.Product
import com.ars.domain.utils.Response
import com.ars.groceriesapp.databinding.ShopOffersImagesVpBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel
import java.util.Date

class ShopEpoxyController(
    private val context: Context,
    private val customer: Customer?,
    private val onCategoryClicked: (category: Category) -> Unit,
    private val onProductClicked: (product: Product) -> Unit,
    private val onAddToCartClick: (productId: Int, onFinish: () -> Unit) -> Unit
) : Typed2EpoxyController<List<Product>?, List<Category>?>() {


    override fun buildModels(
        products: List<Product>?,
        categories: List<Category>?
    ) {

        ShopHeader(customer)
            .id("shop_header")
            .addTo(this)



        carousel {
            id("offers_carousel")
            numViewsToShowOnScreen(1F)
            models(
                listOf(
                    ShopOfferImages().id(1),
                    ShopOfferImages().id(2),
                    ShopOfferImages().id(3)
                )
            )
        }
        val exclusives = products?.filter { it.exclusive }
        val onSale = products?.filter { it.discount != null }?.filter { product ->
            (product.discount?.endDate ?: Date().time) > Date().time
        }
        val mostRated = products?.filter { it.rating > 4 }

        loadProducts(exclusives, "Exclusive Offer","exclusives" )
        loadCategories(categories)
        loadProducts(onSale, "On Sale","onSale" )
        loadProducts(mostRated, "Most Rated","mostRated" )
    }


        private fun loadProducts(products: List<Product>?, productsType: String, sectionId: String) {
            products ?: return
            ShopSection(productsType)
                .id(sectionId)
                .addTo(this)

            val productsModels = mutableListOf<ShopProduct>()


            products.forEachIndexed { index, product ->
                productsModels.add(
                    ShopProduct(product, onProductClicked, onAddToCartClick).apply {
                        id(index)
                    }
                )
            }

            carousel {
                id(productsType + "carousel")
                numViewsToShowOnScreen(2F)
                models(productsModels)
            }
        }

        private fun loadCategories(categories: List<Category>?) {
            categories ?: return
            ShopSection("Popular Categories")
                .id("shop_section_2")
                .addTo(this)
            val categoriesModels = mutableListOf<ShopCategory>()
            categories.forEachIndexed { index, category ->
                categoriesModels.add(
                    ShopCategory(category, onCategoryClicked).apply {
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


    }


data class ShopHeader(
    private val customer: Customer?
) : ViewBindingKotlinModel<ShopHeaderItemBinding>(R.layout.shop_header_item) {
    override fun ShopHeaderItemBinding.bind() {
        if (customer != null) {
            shopHeaderLocation.text = customer.address
        }

    }
}

class ShopOfferImages :
    ViewBindingKotlinModel<ShopOffersImagesVpBinding>(R.layout.shop_offers_images_vp) {
    override fun ShopOffersImagesVpBinding.bind() {

    }
}

data class ShopSection(
    val sectionTitle: String
) : ViewBindingKotlinModel<ShopSectionTitleItemBinding>(R.layout.shop_section_title_item) {
    override fun ShopSectionTitleItemBinding.bind() {
        shopSectionTitleTv.text = sectionTitle
    }
}

data class ShopProduct(
    val product: Product?,
    val onProductClick: (product: Product) -> Unit,
    val onProductAddToCartClick: (productId: Int, onFinish: () -> Unit) -> Unit
) : ViewBindingKotlinModel<ShopProductItemBinding>(R.layout.shop_product_item) {
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

data class ShopCategory(
    val category: Category?,
    val onCategoryClick: (category: Category) -> Unit
) : ViewBindingKotlinModel<PopularCategoryItemBinding>(R.layout.popular_category_item) {
    override fun PopularCategoryItemBinding.bind() {
        if (category != null) {
            popularCategoryProgress.visibility = View.INVISIBLE
            popularCategoryGroup.visibility = View.VISIBLE
            popularCategoryNameTv.text = category.name
            itemBg.setBackgroundColor(Color.parseColor(category.color))

            Glide.with(popularCategoryImageImv).load(category.image).into(popularCategoryImageImv)
            root.setOnClickListener {
                onCategoryClick(category)
            }
        } else {
            popularCategoryProgress.visibility = View.VISIBLE
            popularCategoryGroup.visibility = View.INVISIBLE
        }
    }
}

