package com.ars.groceriesapp.ui.home.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ars.domain.model.Product
import com.ars.groceriesapp.databinding.ShopProductItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SearchProductsAdapter(
    val onProductClick: (productId: Int) -> Unit,
    val onProductAddToCartClick: (productId: Int, onFinish: () -> Unit) -> Unit
) : RecyclerView.Adapter<SearchProductsAdapter.ProductsHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder =
        ProductsHolder(
            ShopProductItemBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false
                )
        )

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        holder.bindProduct(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size


    inner class ProductsHolder(private val binding: ShopProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.rootCv.setOnClickListener {
                onProductClick(differ.currentList[bindingAdapterPosition].id)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bindProduct(product: Product) {
            binding.run {

                shopProductProgress.visibility = View.INVISIBLE
                shopProductItemViewsGroup.visibility = View.VISIBLE
                shopProductNameTv.text = product.name
                shopProductKgPcsTv.text = product.priceUnit
                shopProductPriceTv.text = "$${product.price}"

                Glide.with(shopProductImageImv)
                    .load(product.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(shopProductImageImv)

                shopProductAddBtn.setOnClickListener {
                    shopProductAddBtnProgress.visibility = View.VISIBLE
                    shopProductAddBtn.visibility = View.INVISIBLE
                    onProductAddToCartClick(differ.currentList[bindingAdapterPosition].id) {
                        // On adding product to cart finished
                        shopProductAddBtnProgress.visibility = View.INVISIBLE
                        shopProductAddBtn.visibility = View.VISIBLE

                    }
                }

            }
        }
    }
}
