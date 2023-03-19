package com.ars.groceriesapp.ui.home.search.filter

enum class Sort(val id: Int, val title: String, var selected: Boolean){
    HIGH_RATED(1, "High rated", false),
    NEWEST(2,"Newest", false),
    PRICE_LOW_TO_HIGH(3, "Price: low to high", false),
    PRICE_HIGH_TO_LOW(4, "Price: high to low", false),;

    fun getAll(): List<Sort> {
        return listOf(HIGH_RATED, NEWEST, PRICE_LOW_TO_HIGH, PRICE_HIGH_TO_LOW)
    }
}