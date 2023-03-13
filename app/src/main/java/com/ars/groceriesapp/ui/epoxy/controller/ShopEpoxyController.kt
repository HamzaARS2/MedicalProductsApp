package com.ars.groceriesapp.ui.epoxy.controller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.ars.data.local.entity.ProductEntity
import com.bumptech.glide.Glide
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.PopularCategoryItemBinding
import com.ars.groceriesapp.databinding.ShopHeaderItemBinding
import com.ars.groceriesapp.databinding.ShopProductItemBinding
import com.ars.groceriesapp.databinding.ShopSectionTitleItemBinding
import com.ars.domain.model.Category
import com.ars.domain.model.Customer
import com.ars.domain.model.OnSaleProduct
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import com.ars.groceriesapp.databinding.ShopOffersImagesVpBinding
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel
import java.util.Date

class ShopEpoxyController(
    private val context: Context,
    private val customer: Customer?,
    private val onCategoryClicked: (category: Category) -> Unit,
    private val onProductClicked: (product: Product) -> Unit,
    private val onAddToCartClick: (product: Product, onFinish: () -> Unit) -> Unit
) : TypedEpoxyController<Response<List<Product>?>>() {


    override fun buildModels(response: Response<List<Product>?>) {

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
        val result = response.data
        val exclusives = result?.filter { it.exclusive }
        val onSale = result?.filter { it.discount != null }?.filter { product ->
            (product.discount?.endDate ?: Date().time) > Date().time
        }
        val mostRated = result?.filter { it.rating > 4 }

        when (response) {
            is Response.Success -> {
                displayProducts(exclusives, onSale, mostRated)

            }
            is Response.Error -> {
                displayProducts(exclusives, onSale, mostRated)
            }
            is Response.Loading -> {
                displayProducts(exclusives, onSale, mostRated)
            }
        }


    }

    private fun displayProducts(exclusives: List<Product>?, onSale: List<Product>?, mostRated: List<Product>?) {
        exclusives?.let { loadProducts(it, "Exclusive Offer", "exclusive_section") }
        onSale?.let { loadProducts(it, "On Sale", "onSale_section") }
        mostRated?.let { loadProducts(it, "Most Rated", "mostRated_section") }
    }
    private fun loadProducts(products: List<Product>, productsType: String, sectionId: String) {
        val productsModels = mutableListOf<ShopProduct>()

        ShopSection(productsType)
            .id(sectionId)
            .addTo(this)

        products.forEachIndexed { index, product ->
            productsModels.add(
                ShopProduct(product, ::onProductClick, ::onProductAddToCartClick).apply {
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



}


//    }

//    repeat(2) {
//        exclusiveModels.add(
//            ShopProduct(null,::onProductClick, ::onProductAddToCartClick).apply {
//                id(it)
//            }
//        )
//    }
//
//    carousel {
//        id("exclusive_offer_carousel")
//        numViewsToShowOnScreen(2F)
//        models(exclusiveModels)
//
//    }

//    private fun loadCategories() {
//
//        val categoriesModels = mutableListOf<ShopCategory>()
//
//        ShopSection("Popular Categories")
//            .id("shop_section_2")
//            .addTo(this)
//
//        when (categoriesResource) {
//            is Resource.Success -> {
//                val response = (categoriesResource as Resource.Success<List<Category>?>).result
//                response?.forEachIndexed { index, category ->
//                    categoriesModels.add(
//                        ShopCategory(category, ::onCategoryClick).apply {
//                            id(index)
//                        }
//                    )
//                }
//
//                carousel {
//                    id("popular_categories_carousel")
//                    numViewsToShowOnScreen(1.5F)
//                    models(categoriesModels)
//                }
//
//            }
//            is Resource.Failure -> {
//                Toast.makeText(context, "Error categories", Toast.LENGTH_SHORT).show()
//            }
//            else -> {
//                 // Loading
//                repeat(2) {
//                    categoriesModels.add(
//                        ShopCategory(null, ::onCategoryClick).apply {
//                            id(it)
//                        }
//                    )
//                }
//
//                carousel {
//                    id("popular_categories_carousel")
//                    numViewsToShowOnScreen(1.5F)
//                    models(categoriesModels)
//
//                }
//            }
//        }
//
//
//
//
//    }


//    fun setExclusiveProducts(resource: Resource<List<Product>?>?) {
//        this.exclusiveProductsResource = resource
//        requestModelBuild()
//    }
//
//    fun setOnSaleProducts(resource: Resource<List<OnSaleProduct>?>?) {
//        this.onSaleProductsResource = resource
//        requestModelBuild()
//
//    }
//
//    fun setMostRatedProducts(resource: Resource<List<Product>?>?) {
//        this.mostRatedProductsResource = resource
//        requestModelBuild()
//
//    }
//
//    fun setCategories(resource: Resource<List<Category>?>?) {
//        this.categoriesResource = resource
//        requestModelBuild()
//
//    }
//
//
//
private fun onCategoryClick(category: Category) {
//        onCategoryClicked(category)
}

private fun onProductAddToCartClick(product: Product, onFinish: () -> Unit) {
//        onAddToCartClick(product, onFinish)
}

private fun onProductClick(product: Product) {
//        onProductClicked(product)
}

//}

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
    val onProductAddToCartClick: (product: Product, onFinish: () -> Unit) -> Unit
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
                onProductAddToCartClick(product) {
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
            val color = category.color.substring(0, 1) + "1A" + category.color.substring(1)
            itemBg.setBackgroundColor(Color.parseColor(color))

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

