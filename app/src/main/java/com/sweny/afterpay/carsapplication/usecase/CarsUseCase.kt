package com.sweny.afterpay.carsapplication.usecase

import com.sweny.afterpay.carsapplication.model.CarData
import com.sweny.afterpay.carsapplication.repository.ICarsRepository
import com.sweny.afterpay.carsapplication.utils.orDefault
import javax.inject.Inject


interface ICarsUseCase {
    suspend fun getCars(): List<CarData>
}

/**
 * Responsible for all Cars related business logic
 */
class CarsUseCaseImpl @Inject constructor(private val repo: ICarsRepository) : ICarsUseCase {

    /**
     * Get list of Cars details
     *
     * @return receive list of Cars details
     */
    override suspend fun getCars(): List<CarData> {
        return repo.getCars().map { CarDto ->
                CarData(
                    CarDto.id.orDefault(),
                    CarDto.make.manufacturer,
                    CarDto.make.model,
                    CarDto.year,
                    CarDto.image.orDefault(),
                    CarDto.price
                )
            }
        }
}