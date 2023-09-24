package com.example.e_commerce.data.remoteRetrofit.productItems

data class ItemList(
    val limit: Int,
    val products: List<Items>,
    val skip: Int,
    val total: Int
)