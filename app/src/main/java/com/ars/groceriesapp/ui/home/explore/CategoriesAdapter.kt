package com.ars.groceriesapp.ui.home.explore

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.domain.model.Category
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ExploreCategoryItemBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoriesAdapter(
    val onCategoryClick: (category: Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>() {



    private val differCallBack = object: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {

         return   CategoriesHolder(
                ExploreCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,

                    false
                )
            )
        }


    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        holder.bindCategory(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }



    inner class CategoriesHolder(private val binding: ExploreCategoryItemBinding) :
        ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onCategoryClick(differ.currentList[bindingAdapterPosition])
            }
        }

        fun bindCategory(category: Category) {
            binding.run {
                root.setCardBackgroundColor(Color.parseColor(category.color))
                root.setStrokeColor(ColorStateList.valueOf(Color.parseColor(category.strokeColor)))
                Glide.with(exploreCategoryImageImv).load(category.image)
                    .into(exploreCategoryImageImv)
                exploreCategoryNameTv.text = category.name

            }
        }
    }
}