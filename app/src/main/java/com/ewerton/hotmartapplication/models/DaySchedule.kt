package com.ewerton.hotmartapplication.models

import com.google.gson.annotations.SerializedName

data class DaySchedule(
    @SerializedName("open")
    val open: String?,

    @SerializedName("close")
    val close: String?
)