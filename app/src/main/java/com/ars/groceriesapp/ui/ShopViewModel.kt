package com.ars.groceriesapp.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.Resource
import com.ars.domain.model.Product
import com.ars.domain.usercase.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase
): ViewModel() {

    private val _productsFlow: MutableStateFlow<Resource<List<Product>?>?> = MutableStateFlow(null)
    val products: StateFlow<Resource<List<Product>?>?> get() = _productsFlow

    private val _exclusiveProductsFlow: MutableStateFlow<Resource<List<Product>?>?> = MutableStateFlow(null)
    val exclusiveProducts: StateFlow<Resource<List<Product>?>?> get() = _exclusiveProductsFlow

    val liveProducts: MutableLiveData<List<Product>> = MutableLiveData(emptyList())


    fun fetchProducts() = viewModelScope.launch {
        _productsFlow.value = Resource.Loading
        val allProductsResponse = async { productsUseCase.getAllProducts() }
        val exclusiveProductsResponse = async { productsUseCase.getExclusiveProducts() }
        _productsFlow.emit(allProductsResponse.await())
        _exclusiveProductsFlow.emit(exclusiveProductsResponse.await())
    }





}