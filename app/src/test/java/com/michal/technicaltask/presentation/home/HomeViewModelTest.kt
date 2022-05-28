package com.michal.technicaltask.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.michal.technicaltask.domain.user.all.GetAllUsersUseCase
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.all.model.Users
import com.michal.technicaltask.domain.user.create.AddNewUserUseCase
import com.michal.technicaltask.domain.user.remove.RemoveUserUseCase
import com.michal.technicaltask.presentation.home.adapter.UserAdapterItemMapper
import com.michal.technicaltask.presentation.home.adapter.UserItem
import com.michal.technicaltask.presentation.home.model.UsersViewState
import com.michal.technicaltask.utils.TestSchedulerProvider
import com.michal.technicaltask.utils.verifyNone
import com.michal.technicaltask.utils.whenever
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

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

    private val adapterItems = listOf(
        UserItem(
            id = 1,
            name = "test1",
            email = "email1",
            creationTimeText = ""
        ),
        UserItem(
            id = 2,
            name = "test2",
            email = "email2",
            creationTimeText = ""
        ),
    )

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @Mock
    private lateinit var addNewUserUseCase: AddNewUserUseCase

    @Mock
    private lateinit var removeUserUseCase: RemoveUserUseCase

    @Mock
    private lateinit var userAdapterItemMapper: UserAdapterItemMapper

    @Mock
    private lateinit var testObserver: Observer<UsersViewState>

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        whenever(userAdapterItemMapper.map(testUsers)).thenReturn(adapterItems)
    }

    @Test
    fun `Should get all users mapped to adapter items and emit content state for successful response`() {
        // given
        val expectedContent = UsersViewState.Content(
            userItems = adapterItems,
            loaderVisible = false,
            contentVisible = true,
            emptyContentVisible = false,
            errorVisible = false,
        )
        whenever(getAllUsersUseCase.execute()).thenReturn(Single.just(testUsers))

        // when
        viewModel = HomeViewModel(
            getAllUsersUseCase,
            addNewUserUseCase,
            removeUserUseCase,
            TestSchedulerProvider(),
            userAdapterItemMapper,
        )
        viewModel.usersViewState.observeForever(testObserver)

        // then
        verify(testObserver).onChanged(expectedContent)
        verify(getAllUsersUseCase).execute()
        verify(userAdapterItemMapper).map(testUsers)
    }

    @Test
    fun `Should get empty users list and emit empty content state for successful response`() {
        // given
        val expectedContent = UsersViewState.Content(
            userItems = emptyList(),
            loaderVisible = false,
            contentVisible = false,
            emptyContentVisible = true,
            errorVisible = false,
        )
        val emptyUsers = Users(
            users = emptyList()
        )
        whenever(getAllUsersUseCase.execute()).thenReturn(Single.just(emptyUsers))

        // when
        viewModel = HomeViewModel(
            getAllUsersUseCase,
            addNewUserUseCase,
            removeUserUseCase,
            TestSchedulerProvider(),
            userAdapterItemMapper,
        )
        viewModel.usersViewState.observeForever(testObserver)

        // then
        verify(testObserver).onChanged(expectedContent)
        verify(getAllUsersUseCase).execute()
        verify(userAdapterItemMapper).map(emptyUsers)
    }

    @Test
    fun `Should not map users and emit error state for failed response`() {
        // given
        val expectedError = UsersViewState.Error(
            loaderVisible = false,
            contentVisible = false,
            emptyContentVisible = false,
            errorVisible = true
        )
        val error = Throwable()
        whenever(getAllUsersUseCase.execute()).thenReturn(Single.error(error))

        // when
        viewModel = HomeViewModel(
            getAllUsersUseCase,
            addNewUserUseCase,
            removeUserUseCase,
            TestSchedulerProvider(),
            userAdapterItemMapper,
        )
        viewModel.usersViewState.observeForever(testObserver)

        // then
        verify(testObserver).onChanged(expectedError)
        verify(getAllUsersUseCase).execute()
        verifyNone(userAdapterItemMapper).map(testUsers)
    }

}