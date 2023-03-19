package com.ars.groceriesapp.ui.home.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.CategoryRepository
import com.ars.domain.model.Category
import com.ars.domain.model.Product
import com.ars.domain.usercase.GetCategoriesUseCase
import com.ars.domain.usercase.product.SearchProductsUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
): ViewModel() {


    val categoriesLiveData = getCategoriesUseCase().asLiveData()



}