package com.ars.data.di

import com.ars.data.repository.auth.LoginRepository
import com.ars.data.repository.auth.RegistrationRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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