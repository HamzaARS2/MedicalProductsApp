package com.reddevx.groceriesapp.di.module

import com.reddevx.groceriesapp.data.network.repository.CategoryRepository
import com.reddevx.groceriesapp.data.network.repository.ICRUDRepository
import com.reddevx.groceriesapp.data.network.repository.ProductRepository
import com.reddevx.groceriesapp.data.network.repository.auth.IAuthRepository
import com.reddevx.groceriesapp.data.network.repository.auth.customer.CustomerRepository
import com.reddevx.groceriesapp.model.Category
import com.reddevx.groceriesapp.model.Product
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
    abstract fun bindAuthImpl(repository: CustomerRepository) : IAuthRepository

    @Singleton
    @Binds
    abstract fun bindCrudRepoImpl(repository: CategoryRepository): ICRUDRepository<Category,Int>

}