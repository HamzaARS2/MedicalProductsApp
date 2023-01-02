package com.reddevx.groceriesapp.model

data class Category(
    val id: Int? = null,
    val name: String = "",
    val products:List<Product> = listOf()

)