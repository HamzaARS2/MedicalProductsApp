package com.ars.data.di

import com.ars.data.network.ProductDataSource
import com.ars.data.network.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    private const val BASE_URL = "https://groceries-app-spring-boot-production.up.railway.app/api/"
    private const val BASE_URL = "http://192.168.1.80:8080/api/"


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Singleton
    @Provides
    fun provideCustomerApi(retrofit: Retrofit): CustomerApi =
        retrofit.create(CustomerApi::class.java)

    @Singleton
    @Provides
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi =
        retrofit.create(CategoryApi::class.java)

    @Singleton
    @Provides
    fun provideCartApi(retrofit: Retrofit): CartApi =
        retrofit.create(CartApi::class.java)
    @Singleton
    @Provides
    fun provideFavoriteProductApi(retrofit: Retrofit): FavoriteProductApi =
        retrofit.create(FavoriteProductApi::class.java)
    @Singleton
    @Provides
    fun provideReviewApi(retrofit: Retrofit): ReviewApi =
        retrofit.create(ReviewApi::class.java)

    @Singleton
    @Provides
    fun provideAddressApi(retrofit: Retrofit): AddressApi =
        retrofit.create(AddressApi::class.java)

    @Provides
    fun provideProductDataSource(productApi: ProductApi) =
        ProductDataSource(productApi)




}