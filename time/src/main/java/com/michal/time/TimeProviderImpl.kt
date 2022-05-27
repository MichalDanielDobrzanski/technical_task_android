package com.michal.time

import com.michal.time.model.DateItem
import java.time.LocalDateTime
import javax.inject.Inject

class TimeProviderImpl @Inject constructor() : TimeProvider {

    override fun getNow(): DateItem {
        return DateItem(LocalDateTime.now())
    }
}