package com.michal.technicaltask.data.time

interface TimeRepository {

    fun getNow(): DateItem
}