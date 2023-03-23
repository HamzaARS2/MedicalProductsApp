package com.ars.groceriesapp.ui.home.search.filter

import com.ars.domain.model.Product

class Filter() {

    companion object {
        const val HIGH_RATED = "High rated"
        const val NEWEST = "Newest"
        const val DESCENDING_PRICE = "Price: high to low"
        const val ASCENDING_PRICE = "Price: low to high"
    }

    val appliedFiltersMap: MutableMap<String, Boolean> = mutableMapOf(
        HIGH_RATED to false,
        NEWEST to false,
        DESCENDING_PRICE to false,
        ASCENDING_PRICE to false
    )


    fun sort(products: List<Product>?): List<Product> {
        products ?: return emptyList()
        return products.sortedWith(
            compareByDescending<Product> { if (appliedFiltersMap[HIGH_RATED]!!) it.rating else 0 }
                .thenByDescending { if (appliedFiltersMap[NEWEST]!!) it.createdAt else 0 }
                .thenByDescending { if (appliedFiltersMap[DESCENDING_PRICE]!!) it.price else 0.0 }
                .thenBy { if (appliedFiltersMap[ASCENDING_PRICE]!!) it.price else 0.0 }
        )
    }

    fun getAppliedFiltersNames(): List<String> {
        return appliedFiltersMap.filter { it.value }.toList().map { it.first }.toList()
    }
}
