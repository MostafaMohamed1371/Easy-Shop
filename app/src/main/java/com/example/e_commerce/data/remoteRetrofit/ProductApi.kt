package com.example.e_commerce.data.remoteRetrofit

import com.example.e_commerce.data.remoteRetrofit.productCategory.CategoryList
import com.example.e_commerce.data.remoteRetrofit.productItems.ItemList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    fun getItems(): Call<ItemList>

    @GET("products/categories")
    fun getItemsCategory(): Call<CategoryList>

    @GET("products/category/{name}")
    fun getItemsCategoryItems(@Path("name") name : String): Call<ItemList>

    @GET("products/search")
    fun getItemSearch(@Query("q") SearchItem : String): Call<ItemList>

}