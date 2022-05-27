package com.michal.technicaltask.domain.user.create

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.create.mapper.UserMapper
import com.michal.technicaltask.utils.verifyNone
import com.michal.technicaltask.utils.whenever
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AddNewUserUseCaseTest {

    private val dataUser = UserData(
        id = 1,
        name = "test1",
        email = "email1",
    )

    private val testUser = User(
        id = 1,
        name = "test1",
        email = "email1",
        creationDate = mockk()
    )

    @Mock
    private lateinit var userMapper: UserMapper

    @Mock
    private lateinit var usersRepository: UsersRepository

    private lateinit var addNewUserUseCase: AddNewUserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        addNewUserUseCase = AddNewUserUseCase(
            usersRepository,
            userMapper
        )
    }

    @Test
    fun `Should get the successful response and map it`() {
        // given
        val name = "name"
        val email = "email"
        whenever(usersRepository.addNewUser(name, email)).thenReturn(Single.just(dataUser))
        whenever(userMapper.map(dataUser)).thenReturn(testUser)

        val testObserver = addNewUserUseCase.execute(
            AddNewUserUseCase.Params(
                name = name,
                email = email
            )
        ).test()

        testObserver.assertValue(testUser)
        testObserver.assertComplete()


        verify(usersRepository).addNewUser(name, email)
        verify(userMapper).map(dataUser)
    }

    @Test
    fun `Should emit error for repository failure`() {
        // given
        val name = "name"
        val email = "email"
        val error = Throwable()
        whenever(usersRepository.addNewUser(name, email)).thenReturn(Single.error(error))

        val testObserver = addNewUserUseCase.execute(
            AddNewUserUseCase.Params(
                name = name,
                email = email
            )
        ).test()

        testObserver.assertError(error)
        testObserver.assertNotComplete()

        verify(usersRepository).addNewUser(name, email)
        verifyNone(userMapper).map(dataUser)
    }
}