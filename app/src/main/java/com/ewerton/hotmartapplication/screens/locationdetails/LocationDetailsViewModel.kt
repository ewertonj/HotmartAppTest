package com.ewerton.hotmartapplication.screens.locationdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ewerton.hotmartapplication.models.LocationsDetailsResult
import com.ewerton.hotmartapplication.platform.LiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class LocationDetailsViewModel(
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    private val useCase: LocationDetailsUseCase,
    id: Int?
) : ViewModel(), KoinComponent {

    private val _mLocationDetailsResponse =
        MutableLiveData<LiveDataWrapper<LocationsDetailsResult>>()
    val mLocationDetailsResponse: LiveData<LiveDataWrapper<LocationsDetailsResult>>
        get() = _mLocationDetailsResponse

    private var viewModelJob = Job()
    val mUiScope = CoroutineScope(mainDispatcher + viewModelJob)
    val mIoScope = CoroutineScope(ioDispatcher + viewModelJob)

    init {
        id?.let {
            getLocationDetailsById(id)
        }
    }

    fun getLocationDetailsById(id: Int?) {
        id?.let {
            mUiScope.launch {
                _mLocationDetailsResponse.value = LiveDataWrapper.loading()
                try {
                    val data = useCase.processLocationDetailsUseCase(id)
                    _mLocationDetailsResponse.value = LiveDataWrapper.success(data)
                } catch (e: Exception) {
                    _mLocationDetailsResponse.value = LiveDataWrapper.error(e)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}