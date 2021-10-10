package com.sweny.afterpay.carsapplication.repository

import com.google.gson.Gson
import com.sweny.afterpay.carsapplication.api.CarsApiService
import com.sweny.afterpay.carsapplication.dto.CarDto
import com.sweny.afterpay.carsapplication.dto.CarResponseException
import com.sweny.afterpay.carsapplication.dto.BaseCarResponse
import retrofit2.Response
import javax.inject.Inject

interface ICarsRepository {
    suspend fun getCars(): List<CarDto>
}

/**
 * repository with local cache
 *
 * @property apiService
 * @property data
 *
 */
class CarsRepositoryImpl @Inject constructor(
    val apiService: CarsApiService) : ICarsRepository {

    /**
     * Process api call - helper method to process Api call and extract response body
     *
     * Api errors ae thrown to be processed by view model
     *
     * @param T
     * @param apiCall
     * @receiver
     * @return Response Body
     */
    suspend fun <T> processApiCall(apiCall: suspend () -> Response<T>): T {
        val response: Response<T>
        try {
            response = apiCall()
        } catch (e: Exception) {
            throw CarResponseException(-1, e.message ?: e.toString())
        }
        if (response.isSuccessful) {
            response.body()?.let {
                // Gson().fromJson("{"+it.toString()+"}", Array<CarDto>::class.java).toList()
                return it
            }
        }
        response.errorBody()?.let {
            val ex = parseErrorBody(it.charStream().readText())
            throw  ex
        }
        throw CarResponseException(response.code(), response.message())
    }

    /**
     * Parse error body - helper method to convert error response into CarResponseException
     *
     * @param errorBody
     */
    private fun parseErrorBody(errorBody: String) =
        try {
            val rsp = Gson().fromJson(errorBody, BaseCarResponse::class.java)
            CarResponseException(rsp.status, rsp.message)
        } catch (e: Exception) {
            CarResponseException(-1, e.message ?: e.toString())
        }

    override suspend fun getCars(): List<CarDto> =
       processApiCall {
            apiService.getCarsList()
        }
}




