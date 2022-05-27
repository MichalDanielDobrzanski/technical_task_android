package com.michal.time

import com.michal.time.model.DateItem

interface TimeProvider {

    fun getNow(): DateItem
}