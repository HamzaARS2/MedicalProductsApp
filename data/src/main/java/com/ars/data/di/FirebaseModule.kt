package com.ars.data.di

import android.app.Activity
import android.content.Context
import com.ars.data.repository.auth.LoginRepository
import com.ars.data.repository.auth.RegistrationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideLoginRepo(mAuth: FirebaseAuth) = LoginRepository(mAuth)

    @Provides
    fun provideRegistrationRepo(mAuth: FirebaseAuth) = RegistrationRepository(mAuth)


}