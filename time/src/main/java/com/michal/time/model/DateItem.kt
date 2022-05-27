package com.michal.time.model

import java.time.LocalDateTime
import java.time.ZoneId

data class DateItem(val dateTime: LocalDateTime) {

    val timestamp: Long get() = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}