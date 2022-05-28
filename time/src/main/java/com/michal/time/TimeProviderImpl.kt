package com.michal.time

import com.michal.time.model.DateItem
import java.time.LocalDateTime
import javax.inject.Inject

class TimeProviderImpl @Inject constructor() : TimeProvider {

    private lateinit var appStartTime: DateItem

    override fun storeAppStartTime() {
        appStartTime = getNow()
    }

    override fun getAppStartTime(): DateItem {
        return appStartTime
    }

    override fun getNow(): DateItem {
        return DateItem(LocalDateTime.now())
    }
}