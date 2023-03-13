package com.ars.data.di

import com.ars.data.repository.*
import com.ars.data.repository.auth.FirebaseAuthImpl
import com.ars.data.repository.customer.CustomerRepositoryImpl
import com.ars.data.repository.product.ProductRepositoryImpl
import com.ars.domain.repository.*
import com.ars.domain.repository.auth.IAuthRepository
import com.ars.domain.repository.customer.ICustomerRepository
import com.ars.domain.repository.product.IProductRepository
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

    @Singleton
    @Binds
    abstract fun bindCustomerCartRepository(cartRepositoryImpl: CartRepositoryImpl): ICartRepository
    @Singleton
    @Binds
    abstract fun bindCustomerFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): IFavoritesRepository
    @Singleton
    @Binds
    abstract fun bindCategoryRepository(categoryRepository: CategoryRepository): ICategoryRepository

//    @Binds
//    abstract fun bindCustomerLoginState(customerLoginStateImpl: CustomerLoginStateImpl): ICustomerLoginState


}