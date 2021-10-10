package com.sweny.afterpay.carsapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sweny.afterpay.carsapplication.model.CarData
import com.sweny.afterpay.carsapplication.usecase.ICarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CarsViewModel @Inject constructor(private val CarUseCase: ICarsUseCase) :
    BaseViewModel(){

    private val mCarsList = MutableLiveData<List<CarData>>()
    val CarsList: LiveData<List<CarData>>
        get() = mCarsList

    /**
     * Get all Cars list
     *
     */
    fun refreshCars() = processUseCase {
        mCarsList.value = CarUseCase.getCars()
    }
}
