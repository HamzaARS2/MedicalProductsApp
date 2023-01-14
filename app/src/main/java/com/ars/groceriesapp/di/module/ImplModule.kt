package com.ars.groceriesapp.di.module

import com.ars.groceriesapp.data.network.repository.CategoryRepository
import com.ars.groceriesapp.data.network.repository.IFetchRepository
import com.ars.groceriesapp.data.network.repository.ProductRepository
import com.ars.groceriesapp.data.network.repository.auth.IAuthRepository
import com.ars.groceriesapp.data.network.repository.auth.IProductsProvider
import com.ars.groceriesapp.data.network.repository.auth.customer.CustomerRepository
import com.ars.groceriesapp.model.Category
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

//    @Singleton
//    @Binds
//    abstract fun bindCrudRepoImpl(repository: CategoryRepository): IFetchRepository<Category,Int>

    @Singleton
    @Binds
    abstract fun bindProductRepoImpl(repository: ProductRepository): IProductsProvider

}