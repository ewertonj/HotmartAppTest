package com.ewerton.hotmartapplication.models

import com.google.gson.annotations.SerializedName

data class ScheduleResult (
    @SerializedName("sunday")
    val sunday: DaySchedule?,

    @SerializedName("monday")
    val monday: DaySchedule?,

    @SerializedName("tuesday")
    val tuesday: DaySchedule?,

    @SerializedName("wednesday")
    val wednesday: DaySchedule?,

    @SerializedName("thursday")
    val thursday: DaySchedule?,

    @SerializedName("friday")
    val friday: DaySchedule?,

    @SerializedName("saturday")
    val saturday: DaySchedule?
)