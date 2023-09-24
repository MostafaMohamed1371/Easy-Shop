package com.example.e_commerce.domin.repositry

import com.example.e_commerce.data.localDbRoom.ProductsDataBase
import com.example.e_commerce.data.remoteRetrofit.ProductApi
import com.example.e_commerce.data.remoteRetrofit.productCategory.CategoryList
import com.example.e_commerce.data.remoteRetrofit.productItems.ItemList
import retrofit2.Call
import javax.inject.Inject

class ProductRepositry @Inject constructor(
    val api: ProductApi,
    val  db: ProductsDataBase
) {
    suspend fun getCategories() : Call<CategoryList> {

        return api.getItemsCategory()

    }

    suspend fun getCategoriesItems(name : String) : Call<ItemList> {

        return api.getItemsCategoryItems(name)

    }


    suspend fun getItems(): Call<ItemList> {
        return api.getItems()

    }

    suspend fun getSearch(SearchItem : String): Call<ItemList> {
        return api.getItemSearch(SearchItem)

    }



}