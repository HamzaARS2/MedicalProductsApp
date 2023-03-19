package com.ars.groceriesapp.ui.home.search.filter

import androidx.recyclerview.widget.RecyclerView.NO_POSITION

data class FilterCategory(
    val id: Int,
    val name: String,
    override var selected: Boolean = false,
    override var position: Int = NO_POSITION
): FilterItem
