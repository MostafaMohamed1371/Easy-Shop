package com.example.e_commerce.presention.payment
data class PaymentKeyClaims(
    val amount_cents: Int,
    val currency: String,
    val integration_id: Int,
    val order: Int,
    val user_id: Int
)