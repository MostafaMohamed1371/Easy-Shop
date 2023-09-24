package com.example.e_commerce.data.localDbRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.e_commerce.data.remoteRetrofit.productItems.Items

@TypeConverters(TypeConvertor::class)
@Database(
    entities = [Items::class],
    version = 9,
)
abstract class ProductsDataBase : RoomDatabase() {
    abstract fun getProductDao(): ProductsDao

}