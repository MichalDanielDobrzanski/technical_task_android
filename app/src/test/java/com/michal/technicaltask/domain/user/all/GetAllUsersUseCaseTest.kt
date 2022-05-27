package com.michal.technicaltask.domain.user.all

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.mapper.GetAllUsersMapper
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.all.model.Users
import com.michal.technicaltask.utils.verifyNone
import com.michal.technicaltask.utils.whenever
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GetAllUsersUseCaseTest {

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

    private val testUsers = Users(
        users = listOf(
            User(
                id = 1,
                name = "test1",
                email = "email1",
                creationDate = mockk()
            ),
            User(
                id = 2,
                name = "test2",
                email = "email2",
                creationDate = mockk()
            )
        )
    )

    @Mock
    private lateinit var getAllUsersMapper: GetAllUsersMapper

    @Mock
    private lateinit var usersRepository: UsersRepository

    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getAllUsersUseCase = GetAllUsersUseCase(
            getAllUsersMapper,
            usersRepository
        )
    }

    @Test
    fun `Should get the successful response and map it`() {
        // given
        whenever(usersRepository.getAllUsers()).thenReturn(Single.just(dataUsers))
        whenever(getAllUsersMapper.map(dataUsers)).thenReturn(testUsers)

        val testObserver = getAllUsersUseCase.execute().test()

        testObserver.assertValue(testUsers)
        testObserver.assertComplete()

        verify(usersRepository).getAllUsers()
        verify(getAllUsersMapper).map(dataUsers)
    }

    @Test
    fun `Should emit error for repository failure`() {
        // given
        val error = Throwable()
        whenever(usersRepository.getAllUsers()).thenReturn(Single.error(error))

        val testObserver = getAllUsersUseCase.execute().test()

        testObserver.assertError(error)
        testObserver.assertNotComplete()

        verify(usersRepository).getAllUsers()
        verifyNone(getAllUsersMapper).map(dataUsers)
    }
}