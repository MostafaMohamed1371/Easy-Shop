package com.example.e_commerce.data.remoteRetrofit.productItems

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
data class Items(
    var total_Price:Int=0,
    var counter:Int=0,
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    @PrimaryKey
    val id: Int,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
): Serializable