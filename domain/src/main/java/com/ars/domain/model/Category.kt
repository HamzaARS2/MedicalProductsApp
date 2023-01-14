package com.ars.domain.model

data class Category(
    val id: Int? = null,
    val name: String = "",
    val image: Int? = null,
    val color: String = "#1A53B175",
    val products:List<Product> = listOf()

)