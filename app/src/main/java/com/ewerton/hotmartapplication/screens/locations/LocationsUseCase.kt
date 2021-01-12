package com.ewerton.hotmartapplication.screens.locations

import com.ewerton.hotmartapplication.models.AllLocations
import com.ewerton.hotmartapplication.repository.LocationsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class LocationsUseCase : KoinComponent {

    private val mLocationRepository: LocationsRepository by inject()

    suspend fun processLocationsUseCase(): AllLocations {
        return mLocationRepository.getLocationsData()
    }

}