package com.example.e_commerce.di

import com.example.e_commerce.data.localDbRoom.ProductsDataBase
import com.example.e_commerce.data.remoteRetrofit.ProductApi
import com.example.e_commerce.domin.repositry.ProductRepositry

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//
//@Module
//@InstallIn(SingletonComponent::class)
//object ViewModelMudeol {
//
//
//    @Provides
//    @Singleton
//     fun provideGetRepositry( mealApi: ProductApi,db: ProductsDataBase): ProductRepositry {
//        return ProductRepositry(mealApi,db)
//    }
//}