package com.ars.groceriesapp.ui.home.account

import androidx.lifecycle.ViewModel
import com.ars.data.repository.auth.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {


    fun logout() = loginRepository.logout()

}