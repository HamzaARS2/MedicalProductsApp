package com.ars.data.di

import com.ars.data.repository.CustomerRepositoryImpl
import com.ars.data.repository.FirebaseAuthImpl
import com.ars.data.repository.ProductRepositoryImpl
import com.ars.domain.repository.IAuthRepository
import com.ars.domain.repository.ICustomerRepository
import com.ars.domain.repository.IProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): IProductRepository

    @Binds
    abstract fun bindCustomerRepository(customerRepositoryImpl: CustomerRepositoryImpl): ICustomerRepository

   @Binds
   abstract fun bindFirebaseAuthRepository(firebaseAuthImpl: FirebaseAuthImpl): IAuthRepository

}