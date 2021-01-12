package com.ewerton.hotmartapplication.models

import com.google.gson.annotations.SerializedName

data class AllLocations (
    @SerializedName("listLocations")
    val listLocations: List<AllLocationsResult>
)

