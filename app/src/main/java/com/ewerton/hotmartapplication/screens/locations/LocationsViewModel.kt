package com.ewerton.hotmartapplication.screens.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ewerton.hotmartapplication.models.AllLocations
import com.ewerton.hotmartapplication.platform.LiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class LocationsViewModel(
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    private val useCase: LocationsUseCase
) : ViewModel(), KoinComponent {

    private val _mLocationsResponse = MutableLiveData<LiveDataWrapper<AllLocations>>()
    val mLocationsResponse: LiveData<LiveDataWrapper<AllLocations>>
        get() = _mLocationsResponse

    private var viewModelJob = Job()
    val mUiScope = CoroutineScope(mainDispatcher + viewModelJob)
    val mIoScope = CoroutineScope(ioDispatcher + viewModelJob)

    init {
        getLocations()
    }

    fun getLocations() {
        mUiScope.launch {
            _mLocationsResponse.value = LiveDataWrapper.loading()
            try {
                val data = useCase.processLocationsUseCase()
                _mLocationsResponse.value = LiveDataWrapper.success(data)
            } catch (e: Exception) {
                _mLocationsResponse.value = LiveDataWrapper.error(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}