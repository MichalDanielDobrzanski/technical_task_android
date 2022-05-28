package com.michal.time

import android.text.format.DateUtils
import com.michal.time.model.DateItem
import javax.inject.Inject

class TimeFormatterImpl @Inject constructor(
    private val timeRepository: TimeProvider
) : TimeFormatter {

    override fun formatAsRelativeTimeToAppStart(dateItem: DateItem): String {
        val thenTimestamp = dateItem.timestamp
        val now = timeRepository.getNow().timestamp
        return DateUtils.getRelativeTimeSpanString(
            thenTimestamp,
            now,
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    }
}