package com.ars.domain.usercase

import android.util.Log
import com.ars.domain.repository.IPreferencesHelper
import javax.inject.Inject

class AppLaunchUseCase @Inject constructor(
    private val preferences: IPreferencesHelper
): IPreferencesHelper {
    override fun isFirstTime(): Boolean {
        Log.d("AppLaunchUseCase", "isFirstTime: ${preferences.isFirstTime()}")
        return preferences.isFirstTime()
    }

    override fun setFirstTime(isFirstTime: Boolean) {
        preferences.setFirstTime(isFirstTime)
    }
}