package com.michal.time

import android.text.format.DateUtils
import com.michal.time.model.DateItem
import javax.inject.Inject

class TimeFormatterImpl @Inject constructor(
    private val timeRepository: TimeProvider
) : TimeFormatter {

    override fun formatAsRelativeTimeToNow(dateItem: DateItem): String {
        val thenTimestamp = dateItem.timestamp
        val nowTimestamp = timeRepository.getNow().timestamp
        return DateUtils.getRelativeTimeSpanString(
            thenTimestamp,
            nowTimestamp,
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }

}