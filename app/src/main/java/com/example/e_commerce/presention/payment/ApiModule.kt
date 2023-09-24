package com.example.little_lemon.payment
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object ApiModule {
//
//    @Provides
//    @Singleton
//    @Named("provideOkhttp")
//    fun provideOkhttp(@ApplicationContext context: Context):OkHttpClient{
//
//         val logging = HttpLoggingInterceptor()
//         logging.level = HttpLoggingInterceptor.Level.BODY
//
//        return OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .connectTimeout(20,TimeUnit.MINUTES)
//            .readTimeout(20,TimeUnit.MINUTES)
//            .writeTimeout(20,TimeUnit.MINUTES)
//            .build()
//    }
//
//
//
//
//}