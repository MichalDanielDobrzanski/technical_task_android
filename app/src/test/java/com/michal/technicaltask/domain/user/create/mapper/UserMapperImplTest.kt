package com.michal.technicaltask.domain.user.create.mapper

import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.utils.whenever
import com.michal.time.TimeProvider
import com.michal.time.model.DateItem
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UserMapperImplTest {

    private val nowDateItem: DateItem = mockk {
        every { timestamp } returns 100L
    }

    private val dataUser = UserData(
        id = 1,
        name = "test1",
        email = "email1",
    )

    @Mock
    private lateinit var timeProvider: TimeProvider

    private lateinit var userMapper: UserMapperImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        whenever(timeProvider.getNow()).thenReturn(nowDateItem)
        userMapper = UserMapperImpl(timeProvider)
    }

    @Test
    fun `Should map the user properly`() {
        // when
        val result = userMapper.map(dataUser)

        // then
        assertEquals(
            User(
                id = 1,
                name = "test1",
                email = "email1",
                creationDate = nowDateItem
            ),
            result
        )
    }
}