package com.ars.groceriesapp.ui.home.search

import androidx.lifecycle.*
import com.ars.domain.model.Product
import com.ars.domain.usercase.cart.SaveCartItemUseCase
import com.ars.domain.usercase.product.SearchProductsUseCase
import com.ars.groceriesapp.ui.home.search.filter.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val saveCartItemUseCase: SaveCartItemUseCase
) : ViewModel() {

    var categoryId: Int = 0

    private val currentQuery = MutableLiveData(Pair("",0))
    val products = currentQuery.switchMap {
        val (query, categoryId) = it
        searchProductsUseCase(query, categoryId).asLiveData()
    }

    private val _appliedFilters: MutableLiveData<Filter> = MutableLiveData()
    val appliedFilters: LiveData<Filter> get() = _appliedFilters


    fun updateFilters(filter: Filter) {
        _appliedFilters.value = filter
    }
    fun searchProducts(query: String, categoryId: Int) {
        currentQuery.value = Pair(query, categoryId)
    }

    fun saveCartItem(customerId: String, productId: Int) =
        saveCartItemUseCase(customerId, productId).asLiveData()



}