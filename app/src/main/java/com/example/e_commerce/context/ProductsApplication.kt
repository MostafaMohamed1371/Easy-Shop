package com.example.e_commerce.context
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProductsApplication: Application(){
    init {
        application=this
    }
    companion object{
        private lateinit var application:ProductsApplication
        fun getApplicationContext(): Context =application.applicationContext
    }
}