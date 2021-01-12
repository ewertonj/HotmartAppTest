package com.ewerton.hotmartapplication.network

import com.ewerton.hotmartapplication.models.AllLocations
import com.ewerton.hotmartapplication.models.LocationsDetailsResult
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationsAPIService{

    @GET("locations/")
    suspend fun getLocations(): AllLocations

    @GET("locations/{id}")
    suspend fun getLocationsDetails(@Path("id") id: Int): LocationsDetailsResult

}