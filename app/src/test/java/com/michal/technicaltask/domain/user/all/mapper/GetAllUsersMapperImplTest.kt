package com.michal.technicaltask.domain.user.all.mapper

import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.utils.whenever
import com.michal.time.TimeProvider
import com.michal.time.model.DateItem
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetAllUsersMapperImplTest {

    private val expectedTimestamp = 100L
    private val nowDateItem: DateItem = mockk {
        every { timestamp } returns expectedTimestamp
    }

    private val dataUsers = listOf(
        UserData(
            id = 1,
            name = "test1",
            email = "email1",
        ),
        UserData(
            id = 2,
            name = "test2",
            email = "email2",
        )
    )

    @Mock
    private lateinit var timeProvider: TimeProvider

    private lateinit var getAllUsersMapperImpl: GetAllUsersMapperImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        whenever(timeProvider.getNow()).thenReturn(nowDateItem)
        getAllUsersMapperImpl = GetAllUsersMapperImpl(timeProvider)
    }

    @Test
    fun `Should map users properly`() {
        // when
        val result = getAllUsersMapperImpl.map(dataUsers)

        // then
        assertEquals(2, result.users.size)
        assertEquals(
            User(
                id = 1,
                name = "test1",
                email = "email1",
                creationDate = nowDateItem
            ),
            result.users[0]
        )
        assertEquals(
            User(
                id = 2,
                name = "test2",
                email = "email2",
                creationDate = nowDateItem
            ),
            result.users[1]
        )
    }

    @Test
    fun `Should return empty list for no users`() {
        // when
        val result = getAllUsersMapperImpl.map(emptyList())

        // then
        assertTrue(result.users.isEmpty())
    }
}