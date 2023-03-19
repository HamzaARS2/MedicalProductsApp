package com.ars.groceriesapp.ui.home.search.filter

import androidx.recyclerview.widget.RecyclerView.NO_POSITION

enum class Sort(val id: Int, val title: String): FilterItem{
    HIGH_RATED(1, "High rated") {
        override var selected: Boolean
            get() = false
            set(value) {}
        override var position: Int
            get() = NO_POSITION
            set(value) {}
    },
    NEWEST(2,"Newest") {
        override var selected: Boolean
            get() = false
            set(value) {}
        override var position: Int
            get() = NO_POSITION
            set(value) {}
    },
    PRICE_LOW_TO_HIGH(3, "Price: low to high") {
        override var selected: Boolean
            get() = false
            set(value) {}
        override var position: Int
            get() = NO_POSITION
            set(value) {}
    },
    PRICE_HIGH_TO_LOW(4, "Price: high to low") {
        override var selected: Boolean
            get() = false
            set(value) {}
        override var position: Int
            get() = NO_POSITION
            set(value) {}
    },;

}