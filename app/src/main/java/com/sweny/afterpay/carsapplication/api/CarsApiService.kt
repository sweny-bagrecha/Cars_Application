package com.sweny.afterpay.carsapplication.api

import com.sweny.afterpay.carsapplication.dto.CarDto
import retrofit2.Response
import retrofit2.http.GET

interface CarsApiService {

    @GET("cars.json")
    suspend fun getCarsList(): Response<List<CarDto>>

}