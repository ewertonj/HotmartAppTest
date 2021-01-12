package com.ewerton.hotmartapplication.models

import com.google.gson.annotations.SerializedName

class LocationsDetailsResult(
    @SerializedName("name")
    val name: String,

    @SerializedName("review")
    val review: Double,

    @SerializedName("type")
    val type: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("about")
    val about: String,

    @SerializedName("schedule")
    val schedule: ScheduleResult,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("adress")
    val address: String
)
