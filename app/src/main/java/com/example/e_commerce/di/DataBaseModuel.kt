package com.example.movies2app.di

import android.content.Context
import androidx.room.Room
import com.example.e_commerce.data.localDbRoom.ProductsDao
import com.example.e_commerce.data.localDbRoom.ProductsDataBase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModuel {
    @Provides
    @Singleton
    fun getDataBase(@ApplicationContext context: Context): ProductsDataBase {
        return Room.databaseBuilder(
            context,
            ProductsDataBase::class.java,
            "MyProductsDatabase",
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideMealDao(dataBaseManager: ProductsDataBase): ProductsDao {
        return dataBaseManager.getProductDao()
    }

}