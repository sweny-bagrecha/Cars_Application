package com.sweny.afterpay.carsapplication

import com.google.gson.GsonBuilder
import com.sweny.afterpay.carsapplication.api.ApiConstants
import com.sweny.afterpay.carsapplication.api.CarsApiService
import com.sweny.afterpay.carsapplication.repository.CarsRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CarsGuideUnitTest {

    val BASE_URL = ApiConstants.BASE_URL

    val okHttpClient: OkHttpClient by lazy {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.HEADERS
        logger.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logger)
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val service: CarsApiService by lazy {
        retrofit.create(CarsApiService::class.java)
    }

    val repo by lazy { CarsRepositoryImpl(service) }

    /**
     * unit test to confirm repo function
     * In this case: Result should be the list of CarDto
     *
     */
    @Test
    fun `get cars`() {
        runBlocking {
            try {
                repo.getCars().also {
                    println("-----------------------------------")
                    print(it)
                    println("====================================")
                }
            } catch (ex: Exception) {
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                println(ex)
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                fail(ex.message)
            }
        }
    }
}