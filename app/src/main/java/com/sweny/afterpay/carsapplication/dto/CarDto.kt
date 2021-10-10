package com.sweny.afterpay.carsapplication.dto

import com.google.gson.annotations.SerializedName
import java.lang.Exception

open class BaseCarResponse {
    var success = true
    var status = 0
    val message = ""
}

data class CarDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("make")
    val make: MakeDto,
    @SerializedName("year")
    val year: Int,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("price")
    val price: Int
):BaseCarResponse()

data class MakeDto(
    @SerializedName("manufacturer")
    val manufacturer: String,
    @SerializedName("model")
    val model: String
)

class CarResponseException(val status: Int, val msg: String) :
    Exception("status: $status msg: $msg")
