package com.ars.data.di

import com.ars.data.repository.*
import com.ars.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): IProductRepository

    @Binds
    abstract fun bindCustomerRepository(customerRepositoryImpl: CustomerRepositoryImpl): ICustomerRepository

   @Binds
   abstract fun bindFirebaseAuthRepository(firebaseAuthImpl: FirebaseAuthImpl): IAuthRepository

    @Singleton
    @Binds
    abstract fun bindPreferencesHelper(preferencesHelper: PreferencesHelper): IPreferencesHelper

    @Binds
    abstract fun bindCustomerLoginState(customerLoginStateImpl: CustomerLoginStateImpl): ICustomerLoginState

}