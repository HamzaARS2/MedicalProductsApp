package com.ars.domain.model

import androidx.recyclerview.widget.RecyclerView.NO_POSITION

data class FilterItem(
    val id: Int,
    val name: String,
    val type: String,
    var selected: Boolean = false,
    var position: Int = NO_POSITION
)