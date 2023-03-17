package com.ars.groceriesapp.ui.epoxy.controller

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.ars.domain.model.Product
import com.ars.domain.model.ProductDetails
import com.ars.groceriesapp.ui.epoxy.epoxy_models.product_details.*
import com.ars.groceriesapp.ui.home.product_details.ReviewsAdapter

class ProductDetailsEpoxyController(
    private val context: Context,
    private val onBackClick: () -> Unit,
    private val onAddToCartClick: () -> Unit,
    private val onFavoriteStateChanged: (isChecked: Boolean, onFinish: () -> Unit) -> Unit,
    private val onAddProductToCartClick: (productId: Int, onFinish: () -> Unit) -> Unit,
    private val onProductClick: (product: Product) -> Unit,
) : TypedEpoxyController<ProductDetails>() {
    private val reviewsAdapter: ReviewsAdapter = ReviewsAdapter()


    override fun buildModels(productDetails: ProductDetails?) {
        if (productDetails == null) {
            return
        }
        HeaderImageModel(context, productDetails.image, onBackClick, onAddToCartClick)
            .id("product_details_header")
            .addTo(this)

        PriceModel(
            context,
            productDetails.name,
            productDetails.priceUnit,
            productDetails.price,
            productDetails.isFavorite,
            onFavoriteStateChanged
        )
            .id("product_details_price")
            .addTo(this)

        DescriptionModel(context, productDetails.description)
            .id("product_details_description")
            .addTo(this)

        ReviewsModel(context, reviewsAdapter, productDetails.reviews, productDetails.rating)
            .id("reviews")
            .addTo(this)

        TitleModel(context)
            .id("title")
            .addTo(this)

        displaySimilarProducts(productDetails.similarProducts)

        OrderModel(context)
            .id("product_details_order")
            .addTo(this)
    }

    private fun displaySimilarProducts(products: List<Product?>?) {
        val productsModels = mutableListOf<SimilarProductModel>()

        products?.forEachIndexed { index, product ->
            productsModels.add(
                SimilarProductModel(context, product,onAddProductToCartClick, onProductClick).apply {
                    id(index)
                }
            )
        }


        carousel {
            id("similar_products")
            numViewsToShowOnScreen(2F)
            models(productsModels)
        }
    }



}

