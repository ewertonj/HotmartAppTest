package com.ewerton.hotmartapplication.models

import com.google.gson.annotations.SerializedName

data class AllLocationsResult(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("review")
    val review: Double,

    @SerializedName("type")
    val type: String
)