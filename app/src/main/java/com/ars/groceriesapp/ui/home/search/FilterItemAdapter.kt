package com.ars.groceriesapp.ui.home.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.groceriesapp.databinding.FilterItemBinding

class FilterItemAdapter(private val items: MutableList<String> = mutableListOf()) :
    RecyclerView.Adapter<FilterItemAdapter.FilterItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemHolder =
        FilterItemHolder(
            FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FilterItemHolder, position: Int) {
        holder.bindItem(items[position])
    }


    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(names: List<String>) {
        items.clear()
        items.addAll(names)
        notifyDataSetChanged()
    }


    inner class FilterItemHolder(private val binding: FilterItemBinding) :
        ViewHolder(binding.root) {

        fun bindItem(name: String) {
            binding.filterItemName.text = name
        }
    }


}