package com.ars.groceriesapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.Resource
import com.ars.groceriesapp.model.Customer
import com.ars.groceriesapp.data.network.repository.IFetchRepository
import com.ars.groceriesapp.data.network.repository.auth.IAuthRepository
import com.ars.groceriesapp.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: IAuthRepository,
    private val categoriesRepo: IFetchRepository<Category,Int>
) : ViewModel() {

    private val _customerState: MutableStateFlow<Resource<Customer?>?> = MutableStateFlow(null)
    val customerState: StateFlow<Resource<Customer?>?> get() = _customerState

    private val _loggedIn: MutableLiveData<Boolean> = MutableLiveData(repository.currentUser != null)
    val loggedIn: LiveData<Boolean> get() = _loggedIn

    val categories: MutableLiveData<List<Category>> = MutableLiveData(listOf())


    fun getCategories() = viewModelScope.launch {
//        val response = categoriesRepo.retrieveAll()
//        categories.postValue(response)
    }


    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        _customerState.emit(Resource.Loading)
        val customer = repository.register(name, email, password)
        _customerState.emit(customer)
        saveLoginState()
    }

    fun login(email: String,password: String) = viewModelScope.launch {
        _customerState.value = Resource.Loading
        val customer = repository.login(email, password)
        _customerState.emit(customer)
        saveLoginState()
    }

    fun logout() {
        repository.logout()
        _loggedIn.value = false
    }

    private fun saveLoginState() {
        _loggedIn.value = repository.currentUser != null
    }




}