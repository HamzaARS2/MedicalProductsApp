package com.reddevx.groceriesapp.di.module

import com.reddevx.groceriesapp.data.network.repository.auth.IAuthRepository
import com.reddevx.groceriesapp.data.network.repository.auth.customer.CustomerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ImplModule {

    @Singleton
    @Binds
    abstract fun provideAuthImpl(repository: CustomerRepository) : IAuthRepository
}