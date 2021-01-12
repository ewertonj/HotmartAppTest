package com.ewerton.hotmartapplication.screens.locationdetails

import com.ewerton.hotmartapplication.models.LocationsDetailsResult
import com.ewerton.hotmartapplication.repository.LocationsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class LocationDetailsUseCase : KoinComponent {

    private val mLocationRepository: LocationsRepository by inject()

    suspend fun processLocationDetailsUseCase(path: Int): LocationsDetailsResult {
        return mLocationRepository.getLocationDetailsData(path)
    }
}