package com.reddevx.groceriesapp.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reddevx.groceriesapp.data.network.Resource
import com.reddevx.groceriesapp.data.network.repository.IFetchRepository
import com.reddevx.groceriesapp.data.network.repository.auth.IProductsProvider
import com.reddevx.groceriesapp.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val productRepo: IProductsProvider
): ViewModel() {

    private val _productsFlow: MutableStateFlow<Resource<List<Product>?>?> = MutableStateFlow(null)
    val products: StateFlow<Resource<List<Product>?>?> get() = _productsFlow

    private val _exclusiveProductsFlow: MutableStateFlow<Resource<List<Product>?>?> = MutableStateFlow(null)
    val exclusiveProducts: StateFlow<Resource<List<Product>?>?> get() = _exclusiveProductsFlow

    val liveProducts: MutableLiveData<List<Product>> = MutableLiveData(emptyList())


    fun fetchProducts() = viewModelScope.launch {
        _productsFlow.value = Resource.Loading
        val allProductsResponse = async { productRepo.retrieveAll() }
        val exclusiveProductsResponse = async { productRepo.retrieveExclusive() }
        _productsFlow.emit(allProductsResponse.await())
        _exclusiveProductsFlow.emit(exclusiveProductsResponse.await())
    }





}