package com.example.e_commerce.presention.payment

import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.POST

interface ApiInterface
{
    @POST("auth/tokens")
    suspend fun getToken(@Body api_key: ApiKeyModel):Response<GetTokenResponse>

    @POST("ecommerce/orders")
    suspend fun getOrder(@Body orderRequest: OrderRequest):Response<OrderResponce>

    @POST("acceptance/payment_keys")
    suspend fun paymentRequest(@Body paymentRequest: PaymentRequest):Response<GetTokenResponse>

//    @POST("acceptance/payments/pay")
//    suspend fun paymentKiosk(@Body kioskRequest: KioskRequest):Response<JsonElement>

}