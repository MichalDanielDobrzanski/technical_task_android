package com.michal.technicaltask.utils

import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.stubbing.OngoingStubbing
import retrofit2.Response

internal fun <T> whenever(methodCall: T): OngoingStubbing<T> = `when`(methodCall)

internal fun <T> verifyNone(mock: T): T = verify(mock, times(0))

internal fun <T> T.toSuccessfulResponseMock(): Response<T> = mockk {
    every { isSuccessful } returns true
    every { body() } returns this@toSuccessfulResponseMock
}

fun <T> T.toFailedResponseMock(): Response<T> = mockk {
    every { isSuccessful } returns false
    every { body() } returns this@toFailedResponseMock
}

fun <T> Observer<T>.verifyCapturedArgumentsInOrder(
    argumentCaptor: ArgumentCaptor<T>,
    vararg args: T
) {
    verify(this, times(args.size)).onChanged(argumentCaptor.capture())
    args.forEachIndexed { index, arg: T ->
        assertEquals(arg, argumentCaptor.allValues[index])
    }
}