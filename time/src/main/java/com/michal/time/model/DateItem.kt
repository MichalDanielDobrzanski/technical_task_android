package com.michal.time.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.ZoneId

@Parcelize
data class DateItem(val dateTime: LocalDateTime) : Parcelable {

    val timestamp: Long get() = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}