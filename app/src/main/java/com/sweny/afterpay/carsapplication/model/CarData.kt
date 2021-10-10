package com.sweny.afterpay.carsapplication.model

data class CarData(
    val id: String,
    val manufacturer: String,
    val model: String,
    val year: Int,
    val image: String? = null,
    val price: Int
)
