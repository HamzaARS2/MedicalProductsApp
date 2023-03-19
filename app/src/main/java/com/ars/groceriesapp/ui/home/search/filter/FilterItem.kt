package com.ars.groceriesapp.ui.home.search.filter

import androidx.recyclerview.widget.RecyclerView.NO_POSITION

interface FilterItem {
    var selected: Boolean
    var position: Int
}