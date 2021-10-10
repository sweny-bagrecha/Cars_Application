package com.sweny.afterpay.carsapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweny.afterpay.carsapplication.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * Base view model encapsulate error handling functionality
 * from business and data layers
 *
 * All viewmodels in the Cars journey must extend this class
 *
 * viewmodels can use apiErrorHandler to easily handle all exceptions
 * thrown in use cases as long as corresponding fragments extend
 * from BaseTripFragment
 *
 */
abstract class BaseViewModel : ViewModel() {

    val apiError = SingleLiveEvent<String>()

    private val mLoadingSpinner = MutableLiveData<Boolean>()
    val loadingSpinner: LiveData<Boolean>
        get() = mLoadingSpinner

    private val apiErrorHandler = CoroutineExceptionHandler { _, ex ->
        ex.message?.let {
            mLoadingSpinner.value = false
                apiError.value = it
        } ?: run {
            mLoadingSpinner.value = false
            apiError.value = "Unknown network error"
        }
    }

    /**
     * Checks for network connectivity
     * If available then calls loading spinner
     *
     * @param useCase
     */
    protected fun processUseCase(useCase: suspend () -> Unit) {
        viewModelScope.launch(apiErrorHandler) {
            mLoadingSpinner.value = true
            useCase()
            mLoadingSpinner.value = false
        }
    }
}

