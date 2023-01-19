package com.ars.data.di

import android.content.Context
import android.content.SharedPreferences
import com.ars.data.repository.PreferencesHelper
import com.ars.domain.repository.IPreferencesHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    private object Constants {
        const val APP_PREFERENCES = "GroceriesPreferences"
    }


    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.APP_PREFERENCES,Context.MODE_PRIVATE)


}