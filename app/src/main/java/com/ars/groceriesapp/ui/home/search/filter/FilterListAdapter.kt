package com.ars.groceriesapp.ui.home.search.filter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.domain.model.FilterItem
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FilterItemBinding

class FilterListAdapter(
    private val context: Context,
    var items: MutableList<FilterItem> = mutableListOf()
) :
    RecyclerView.Adapter<FilterListAdapter.FilterListHolder>() {

    private var selectedPos = NO_POSITION
    private var lastSelectedPos = NO_POSITION


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterListHolder =
        FilterListHolder(
            FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FilterListHolder, position: Int) {
        val item = items[position]
        holder.bindItem(item)

        if (item.selected) {
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

    fun resetList() {
        items.onEach { it.selected = false }
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<FilterItem>) {
        data.forEachIndexed { index, filterItem ->
            if (filterItem.selected) lastSelectedPos = index }

        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class FilterListHolder(val binding: FilterItemBinding) :
        ViewHolder(binding.root) {

        init {
            binding.filterItemName.setOnClickListener {
                if (lastSelectedPos != NO_POSITION) {
                    items[lastSelectedPos].selected = false
                    notifyItemChanged(lastSelectedPos)
                }
                if(bindingAdapterPosition != lastSelectedPos) {
                    items[bindingAdapterPosition].selected = true
                    notifyItemChanged(bindingAdapterPosition)
                }
                lastSelectedPos = bindingAdapterPosition
            }
        }


        fun bindItem(item: FilterItem) {
           binding.filterItemName.text = item.name
        }


    }


}