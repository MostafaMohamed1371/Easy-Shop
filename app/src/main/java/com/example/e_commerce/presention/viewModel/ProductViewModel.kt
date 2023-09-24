package com.example.e_commerce.presention.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.remoteRetrofit.productCategory.CategoryList
import com.example.e_commerce.data.remoteRetrofit.productItems.ItemList
import com.example.e_commerce.data.remoteRetrofit.productItems.Items
import com.example.e_commerce.domin.repositry.ProductRepositry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductViewModel@Inject constructor (
    private val repositry: ProductRepositry
):ViewModel()
{
    private var itemsLiveData= MutableLiveData<List<Items?>>()
    private var categoryLiveData= MutableLiveData<CategoryList?>()
    private var categoryItemsLiveData= MutableLiveData<List<Items?>>()
    private var searchProductLiveData=MutableLiveData<List<Items>?>()
    private  var favoriteProductLiveData=repositry.db.getProductDao().getAllProduct()








    fun getItems() {
        viewModelScope.launch {
            repositry.getItems().enqueue(object : retrofit2.Callback<ItemList> {
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    if (response.body() != null) {
                        val items: List<Items> = response.body()!!.products
                        itemsLiveData.value = items

                    }
                }

                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    Log.d("ItemsProduct", t.message.toString())
                }

            })
        }
    }


    fun getCategories() {
        viewModelScope.launch {
            repositry.getCategories().enqueue(object : retrofit2.Callback<CategoryList> {
                override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                    if (response.body() != null) {
                        val categories: CategoryList? = response.body()
                        categoryLiveData.value = categories

                    }
                }

                override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                    Log.d("CategoriesProduct", t.message.toString())
                }

            })
        }
    }


    fun getCategoriesItems(name:String) {
        viewModelScope.launch {
            repositry.getCategoriesItems(name).enqueue(object : retrofit2.Callback<ItemList> {
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    if (response.body() != null) {
                        val categoriesPhone: List<Items> = response.body()!!.products
                        categoryItemsLiveData.value = categoriesPhone

                    }
                }

                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    Log.d("CategoriesPhoneProduct", t.message.toString())
                }

            })
        }
    }


    fun getSearch(search:String) {
        viewModelScope.launch {
            repositry.getSearch(search).enqueue(object : retrofit2.Callback<ItemList> {
                override fun onResponse(
                    call: Call<ItemList>,
                    response: Response<ItemList>
                ) {
                    if (response.body() != null) {
                        val search = response.body()?.products
                        search?.let {
                            searchProductLiveData.postValue(it as List<Items>)
                        }

                    }
                }

                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    Log.d("SearchProduct", t.message.toString())
                }

            })
        }
    }







    fun observeitemsLiveData(): LiveData<List<Items?>> {
        return itemsLiveData
    }


    fun observeCategoryLiveData(): LiveData<CategoryList?> {
        return categoryLiveData
    }

    fun observeCategoryItemsLiveData(): LiveData<List<Items?>> {
        return categoryItemsLiveData
    }

    fun observeSearchProductLiveData(): LiveData<List<Items>?> {
        return searchProductLiveData
    }


    fun upinsertProduct(items: Items){

            repositry.db.getProductDao().upsertProduct(items)

    }



    fun deleteProduct(items: Items){

        repositry.db.getProductDao().deleteProduct(items)

    }

    fun observeFavoriteProductsLiveData(): LiveData<List<Items>> {
        return favoriteProductLiveData
    }





}