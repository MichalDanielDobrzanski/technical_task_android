package com.michal.time

import com.michal.time.model.DateItem

interface TimeFormatter {
    fun formatAsRelativeTimeToAppStart(dateItem: DateItem): String
}