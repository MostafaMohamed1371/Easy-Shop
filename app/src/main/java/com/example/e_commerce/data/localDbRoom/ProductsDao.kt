package com.example.e_commerce.data.localDbRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerce.data.remoteRetrofit.productItems.Items


@Dao
interface ProductsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upsertProduct(productItems: Items)
    @Delete
     fun deleteProduct(productItem: Items)
    @Query("SELECT * FROM items")
    fun getAllProduct(): LiveData<List<Items>>


}