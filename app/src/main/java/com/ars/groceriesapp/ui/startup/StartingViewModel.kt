package com.ars.groceriesapp.ui.startup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ars.data.repository.auth.LoginRepository
import com.ars.domain.model.Customer
import com.ars.domain.usercase.AppLaunchUseCase
import com.ars.domain.usercase.customer.GetCustomerUseCase
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
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


    fun isFirstTimeLaunch() = appLaunchUseCase.isFirstTime()
    fun setIsFirstTimeLaunch(isFirstTime: Boolean) {
        appLaunchUseCase.setFirstTime(isFirstTime)

    }

    fun getCustomer(id: String) =
        getCustomerUseCase(id).asLiveData()

    fun isLoggedIn() = loginRepo.isLoggedIn

}