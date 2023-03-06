package com.ars.groceriesapp.ui.home.explore

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.CategoryRepository
import com.ars.domain.model.Category
import com.ars.domain.model.Product
import com.ars.domain.usercase.product.GetExclusiveProductsUseCase
import com.ars.domain.usercase.product.SearchProductsUseCase
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.ui.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val categoriesRepository: CategoryRepository,
    private val searchProductsUseCase: SearchProductsUseCase
): ViewModel() {


    private val _categoriesFlow: MutableStateFlow<Resource<List<Category>?>?> = MutableStateFlow(null)
    val categoriesFlow: StateFlow<Resource<List<Category>?>?> get() = _categoriesFlow

    private val _productsFlow: MutableSharedFlow<Resource<List<Product>?>?> = MutableSharedFlow()
    val productsFlow: SharedFlow<Resource<List<Product>?>?> get() = _productsFlow

    init {
        fetchCategories()
    }

    private fun fetchCategories() = viewModelScope.launch {
        _categoriesFlow.value = Resource.Loading
        val response = categoriesRepository.retrieveAll()
        _categoriesFlow.emit(response)
    }

    fun searchProduct(query: String) = viewModelScope.launch {
        _productsFlow.emit(Resource.Loading)
        val response = searchProductsUseCase(query)
        _productsFlow.emit(response)
    }


}