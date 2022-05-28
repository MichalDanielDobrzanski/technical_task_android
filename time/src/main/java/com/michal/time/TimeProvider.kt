package com.michal.time

import com.michal.time.model.DateItem

interface TimeProvider {

    fun storeAppStartTime()

    fun getAppStartTime(): DateItem

    fun getNow(): DateItem
}