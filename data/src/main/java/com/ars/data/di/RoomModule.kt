package com.ars.data.di

import android.app.Application
import androidx.room.Room
import com.ars.data.local.GroceriesDatabase
import com.ars.data.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): GroceriesDatabase =
        Room.databaseBuilder(application, GroceriesDatabase::class.java, "groceries_db")
            .build()

    @Provides
    @Singleton
    fun provideProductsDao(database: GroceriesDatabase): ProductDao =
        database.getProductDao()

}
