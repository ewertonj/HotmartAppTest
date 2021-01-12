package com.ewerton.hotmartapplication.repository

import com.ewerton.hotmartapplication.models.AllLocations
import com.ewerton.hotmartapplication.models.LocationsDetailsResult
import com.ewerton.hotmartapplication.network.LocationsAPIService
import org.koin.core.KoinComponent
import org.koin.core.inject

class LocationsRepository: KoinComponent {

    private val mLocationsAPIService: LocationsAPIService by inject()

    suspend fun getLocationsData(): AllLocations {
        return mLocationsAPIService.getLocations()
    }

    suspend fun getLocationDetailsData(path: Int): LocationsDetailsResult {
        return mLocationsAPIService.getLocationsDetails(path)
    }
}