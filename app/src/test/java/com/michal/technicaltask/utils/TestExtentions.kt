package com.michal.technicaltask.utils

import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.stubbing.OngoingStubbing

internal fun <T> whenever(methodCall: T): OngoingStubbing<T> = `when`(methodCall)

internal fun <T> verifyNone(mock: T): T = verify(mock, times(0))