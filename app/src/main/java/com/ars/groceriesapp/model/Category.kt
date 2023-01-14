package com.ars.groceriesapp.model

import com.ars.groceriesapp.R


data class Category(
    val id: Int? = null,
    val name: String = "",
    val image: Int = R.drawable.fruits_veg_category_image,
    val color: String = "#1A53B175",
    val products:List<Product> = listOf()

)