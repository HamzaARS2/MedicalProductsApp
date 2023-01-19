package com.ars.domain.usercase

import com.ars.domain.repository.IPreferencesHelper
import javax.inject.Inject

class AppLaunchUseCase @Inject constructor(
    private val preferences: IPreferencesHelper
): IPreferencesHelper {
    override fun isFirstTime(): Boolean {
        return preferences.isFirstTime()
    }

    override fun setFirstTime(isFirstTime: Boolean) {
        preferences.setFirstTime(isFirstTime)
    }
}