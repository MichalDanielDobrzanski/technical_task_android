package com.michal.technicaltask.domain.user.remove

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.utils.whenever
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class RemoveUserUseCaseTest {

    @Mock
    private lateinit var usersRepository: UsersRepository

    private lateinit var removeUserUseCase: RemoveUserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        removeUserUseCase = RemoveUserUseCase(
            usersRepository
        )
    }

    @Test
    fun `Should get the successful response and map it`() {
        // given
        val userId = 123
        whenever(usersRepository.removeUser(userId)).thenReturn(Completable.complete())

        // when
        val testObserver = removeUserUseCase.execute(userId).test()

        // then
        testObserver.assertComplete()

        verify(usersRepository).removeUser(userId)
    }

    @Test
    fun `Should emit error for repository failure`() {
        // given
        val userId = 123
        val error = Throwable()
        whenever(usersRepository.removeUser(userId)).thenReturn(Completable.error(error))

        // when
        val testObserver = removeUserUseCase.execute(userId).test()

        // then
        testObserver.assertError(error)
        testObserver.assertNotComplete()

        verify(usersRepository).removeUser(userId)
    }
}