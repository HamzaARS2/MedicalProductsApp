package com.ars.domain.repository

interface IPreferencesHelper {
    fun isFirstTime(): Boolean
    fun setFirstTime(isFirstTime: Boolean)
}