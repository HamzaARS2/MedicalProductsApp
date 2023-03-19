package com.ars.groceriesapp.ui.home.search.filter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.domain.model.Category
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FilterItemBinding

class FilterListAdapter<T>(
    private val context: Context,
    private var items: List<T> = emptyList()
) :
    RecyclerView.Adapter<FilterListAdapter<T>.FilterListHolder>() {

    private var selectedPos = NO_POSITION
    private var lastSelectedPos = NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterListHolder =
        FilterListHolder(
            FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FilterListHolder, position: Int) {
        holder.bindItem(items[position])
        if (position == selectedPos) {
            holder.binding.apply {
                filterItemName.setTextColor(getColor())
                root.strokeColor = getColor()
            }
        } else {
            holder.binding.apply {
                filterItemName.setTextColor(Color.parseColor("#A6A5A5"))
                root.strokeColor = Color.parseColor("#A6A5A5")
            }
        }
    }

    private fun getColor() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(R.color.main_btn_color, context.theme)
        } else {
            context.resources.getColor(R.color.main_btn_color)
        }


    override fun getItemCount(): Int = items.size

    fun getSelectedItem(): T? = if (selectedPos == NO_POSITION) null
    else items[selectedPos]

    fun getSelectedPos() = selectedPos

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<T>) {
        items = data
        notifyDataSetChanged()
    }

    inner class FilterListHolder(val binding: FilterItemBinding) :
        ViewHolder(binding.root) {

        init {
            binding.filterItemName.setOnClickListener {
                items[bindingAdapterPosition]
                lastSelectedPos = selectedPos
                selectedPos = bindingAdapterPosition
                notifyItemChanged(selectedPos)
                if (lastSelectedPos != NO_POSITION)
                    notifyItemChanged(lastSelectedPos)
            }
        }


        fun bindItem(item: T) {
            when (item) {
                is String -> {
                    binding.filterItemName.text = item
                }
                is Category -> {
                    binding.filterItemName.text = item.name
                }
            }
        }

        fun setSelectedItem(position: Int) {
            selectedPos = position
            notifyItemChanged(position)
        }

    }


}