package com.reddevx.groceriesapp.di.module

import com.google.firebase.auth.FirebaseAuth
import com.reddevx.groceriesapp.data.network.repository.auth.IAuthRepository
import com.reddevx.groceriesapp.data.network.repository.auth.customer.CustomerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun getFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


}