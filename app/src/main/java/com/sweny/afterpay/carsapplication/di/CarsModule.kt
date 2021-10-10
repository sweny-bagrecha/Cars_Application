package com.sweny.afterpay.carsapplication.di

import com.sweny.afterpay.carsapplication.repository.CarsRepositoryImpl
import com.sweny.afterpay.carsapplication.repository.ICarsRepository
import com.sweny.afterpay.carsapplication.usecase.CarsUseCaseImpl
import com.sweny.afterpay.carsapplication.usecase.ICarsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CarsModule {

    @Singleton
    @Binds
    abstract fun bindCarsRepo(
        carsRepo: CarsRepositoryImpl
    ) : ICarsRepository

   @Binds
    abstract fun bindCarsUseCase(
        carsUseCase: CarsUseCaseImpl
    ): ICarsUseCase
}