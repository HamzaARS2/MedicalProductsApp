package com.ars.data.di

import com.ars.data.remote.ProductDataSource
import com.ars.data.remote.api.CustomerApi
import com.ars.data.remote.api.ProductApi
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

    private const val BASE_URL = "http://192.168.1.5:8888"


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

    @Provides
    fun provideProductDataSource(productApi: ProductApi) =
        ProductDataSource(productApi)




}