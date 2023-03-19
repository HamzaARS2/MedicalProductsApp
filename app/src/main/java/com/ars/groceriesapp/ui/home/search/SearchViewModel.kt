package com.ars.groceriesapp.ui.home.search

import androidx.lifecycle.*
import com.ars.groceriesapp.ui.home.search.filter.FilterCategory
import com.ars.domain.usercase.GetCategoriesUseCase
import com.ars.domain.usercase.cart.SaveCartItemUseCase
import com.ars.domain.usercase.product.SearchProductsUseCase
import com.ars.domain.utils.toFilterCategory
import com.ars.groceriesapp.ui.home.search.filter.Sort
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val saveCartItemUseCase: SaveCartItemUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val currentQuery = MutableLiveData(Pair("", 0))
    val products = currentQuery.switchMap {
        val (query, categoryId) = it
        searchProductsUseCase(query, categoryId).asLiveData()
    }

    private val _appliedFilter: MutableLiveData<Pair<Sort?, FilterCategory?>> = MutableLiveData(Pair(null,null))
    val appliedFilter: LiveData<Pair<Sort?, FilterCategory?>> get() =  _appliedFilter

    private val _filterCategories: MutableLiveData<List<FilterCategory>?> = MutableLiveData(null)
    val filterCategory: LiveData<List<FilterCategory>?> get() = _filterCategories

    private val _sortValues: MutableLiveData<List<Sort>?> = MutableLiveData(null)
    val sortValues: LiveData<List<Sort>?> get() = _sortValues

    init {
       _filterCategories.postValue(getCategoriesUseCase().asLiveData().value?.data
           ?.map { it.toFilterCategory() })

        _sortValues.value = getSortingOptions()

    }


    fun searchProducts(query: String, categoryId: Int) {
        currentQuery.value = Pair(query, categoryId)
    }

    fun saveCartItem(customerId: String, productId: Int) =
        saveCartItemUseCase(customerId, productId).asLiveData()

    fun getCategories() =
        getCategoriesUseCase().asLiveData()



    fun updateAppliedFilter(pair: Pair<Sort?, FilterCategory?>) {
        _appliedFilter.value = pair
    }

    fun resetFilter() {
        _appliedFilter.value = Pair(null, null)
    }

    private fun getSortingOptions() = listOf(
        Sort.HIGH_RATED,
        Sort.NEWEST,
        Sort.PRICE_LOW_TO_HIGH,
        Sort.PRICE_HIGH_TO_LOW
    )


}