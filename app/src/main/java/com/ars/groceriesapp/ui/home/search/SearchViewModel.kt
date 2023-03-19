package com.ars.groceriesapp.ui.home.search

import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.ars.domain.model.FilterItem
import com.ars.domain.usercase.GetCategoriesUseCase
import com.ars.domain.usercase.cart.SaveCartItemUseCase
import com.ars.domain.usercase.product.SearchProductsUseCase
import com.ars.domain.utils.asFilterItem
import com.ars.groceriesapp.ui.home.search.filter.Sort
import com.ars.groceriesapp.utils.FILTER_CATEGORY_TYPE
import com.ars.groceriesapp.utils.FILTER_SORTING_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val saveCartItemUseCase: SaveCartItemUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    var lastSelectedPos = NO_POSITION

    private val currentQuery = MutableLiveData(Pair("", 0))
    val products = currentQuery.switchMap {
        val (query, categoryId) = it
        searchProductsUseCase(query, categoryId).asLiveData()
    }

    private val _filterCategories: MutableLiveData<List<FilterItem>?> = MutableLiveData(null)
    val filterCategory: LiveData<List<FilterItem>?> get() = _filterCategories

    private val _sortValues: MutableLiveData<List<FilterItem>?> = MutableLiveData(null)
    val sortValues: LiveData<List<FilterItem>?> get() = _sortValues

    init {

        viewModelScope.launch {
            getCategoriesUseCase().collectLatest { response ->
                val result = response.data?.map { it.asFilterItem(FILTER_CATEGORY_TYPE) }
                _filterCategories.postValue(result)
            }
        }

        _sortValues.value = getSortingOptions()

    }


    fun searchProducts(query: String, categoryId: Int) {
        currentQuery.value = Pair(query, categoryId)
    }

    fun saveCartItem(customerId: String, productId: Int) =
        saveCartItemUseCase(customerId, productId).asLiveData()

    fun updateFilterLists(categoryUpdatedList: List<FilterItem>,sortingUpdatedList: List<FilterItem> ) {
        _filterCategories.value = categoryUpdatedList
        _sortValues.value = sortingUpdatedList
    }




    private fun getSortingOptions() = listOf(
       FilterItem(Sort.HIGH_RATED.id, Sort.HIGH_RATED.title, FILTER_SORTING_TYPE),
       FilterItem(Sort.NEWEST.id, Sort.NEWEST.title, FILTER_SORTING_TYPE),
       FilterItem(Sort.PRICE_LOW_TO_HIGH.id, Sort.PRICE_LOW_TO_HIGH.title, FILTER_SORTING_TYPE),
       FilterItem(Sort.PRICE_HIGH_TO_LOW.id, Sort.PRICE_HIGH_TO_LOW.title, FILTER_SORTING_TYPE)
    )


}