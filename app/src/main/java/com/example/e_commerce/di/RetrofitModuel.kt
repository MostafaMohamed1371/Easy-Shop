package com.example.e_commerce.di


import com.example.e_commerce.data.constant.Constant
import com.example.e_commerce.data.remoteRetrofit.ProductApi
import com.example.e_commerce.presention.payment.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  RetrofitModuel {


    @Provides
    @Singleton
    fun provideHttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            . retryOnConnectionFailure(true)
            .readTimeout(30, TimeUnit.SECONDS)
            . connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }


    @Provides
    @Singleton
    @Named("provideRetrofit")
    fun provideRetrofitPayMob(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL2)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService (@Named("provideRetrofit") retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}