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
                val mainColor = category.color
                val bgColor =
                    mainColor.substring(0, 1) + "1A" + mainColor.substring(1, mainColor.length)
                val strokeColor =
                    mainColor.substring(0, 1) + "4A" + mainColor.substring(1, mainColor.length)
                root.setCardBackgroundColor(Color.parseColor(bgColor))
                root.setStrokeColor(ColorStateList.valueOf(Color.parseColor(strokeColor)))
                Glide.with(exploreCategoryImageImv).load(category.image)
                    .into(exploreCategoryImageImv)
                exploreCategoryNameTv.text = category.name

            }
        }
    }
}