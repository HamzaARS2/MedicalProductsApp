package com.ars.groceriesapp.ui.startup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.auth.LoginRepository
import com.ars.domain.model.Customer
import com.ars.domain.usercase.AppLaunchUseCase
import com.ars.domain.usercase.customer.GetCustomerUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(
    private val appLaunchUseCase: AppLaunchUseCase,
    private val loginRepo: LoginRepository,
    private val getCustomerUseCase: GetCustomerUseCase
): ViewModel() {

//    val customerDocId: String? get() = customerLoginState.getCustomerDocId()

    private val _customerFlow: MutableSharedFlow<Resource<Customer>> = MutableSharedFlow()
    val customerFlow: SharedFlow<Resource<Customer>> get() = _customerFlow

    fun isFirstTimeLaunch() = appLaunchUseCase.isFirstTime()
    fun setIsFirstTimeLaunch(isFirstTime: Boolean) {
        appLaunchUseCase.setFirstTime(isFirstTime)
    }

    fun getCustomer(id: String) = viewModelScope.launch {
        _customerFlow.tryEmit(Resource.Loading)
        _customerFlow.emit(getCustomerUseCase(id))
    }

    fun isLoggedIn() = loginRepo.isLoggedIn

}