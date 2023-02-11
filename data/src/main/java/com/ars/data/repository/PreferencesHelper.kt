package com.ars.data.repository

import android.content.SharedPreferences
import com.ars.domain.repository.IPreferencesHelper
import javax.inject.Inject

class PreferencesHelper @Inject constructor(
    private val sp: SharedPreferences
): IPreferencesHelper {

   private object Constants {
        const val IS_FIRST_TIME_LAUNCH = "FirstTimeLaunch"
    }
    override fun isFirstTime(): Boolean {
        return sp.getBoolean(Constants.IS_FIRST_TIME_LAUNCH,true)
    }

    override fun setFirstTime(isFirstTime: Boolean) {
        sp.edit().putBoolean(Constants.IS_FIRST_TIME_LAUNCH,isFirstTime)
            .apply()
    }
}