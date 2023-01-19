package com.ars.groceriesapp.ui.startup

import androidx.lifecycle.ViewModel
import com.ars.domain.repository.IAuthRepository
import com.ars.domain.usercase.AppLaunchUseCase
import com.ars.domain.usercase.CustomerLoginStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val appLaunchUseCase: AppLaunchUseCase,
    private val customerLoginState: CustomerLoginStateUseCase
): ViewModel() {

    val customerDocId: String? get() = customerLoginState.getCustomerDocId()

    fun isFirstTimeLaunch() = appLaunchUseCase.isFirstTime()
    fun setIsFirstTimeLaunch(isFirstTime: Boolean) {
        appLaunchUseCase.setFirstTime(isFirstTime)
    }

    fun isLoggedIn() = customerLoginState.isLoggedIn()

}