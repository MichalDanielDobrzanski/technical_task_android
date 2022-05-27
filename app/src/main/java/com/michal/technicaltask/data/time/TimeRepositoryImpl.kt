package com.michal.technicaltask.data.time

import java.time.LocalDateTime
import javax.inject.Inject

class TimeRepositoryImpl @Inject constructor() : TimeRepository {

    override fun getNow(): DateItem {
        return DateItem(LocalDateTime.now())
    }
}