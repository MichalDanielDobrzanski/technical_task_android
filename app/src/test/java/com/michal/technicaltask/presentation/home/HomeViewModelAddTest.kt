package com.michal.technicaltask.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.michal.technicaltask.domain.refresh.RefreshContentUseCase
import com.michal.technicaltask.domain.user.all.GetAllUsersUseCase
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.all.model.Users
import com.michal.technicaltask.domain.user.create.AddNewUserUseCase
import com.michal.technicaltask.domain.user.remove.RemoveUserUseCase
import com.michal.technicaltask.presentation.home.adapter.UserAdapterItemMapper
import com.michal.technicaltask.presentation.home.adapter.UserItem
import com.michal.technicaltask.presentation.home.model.UsersViewState
import com.michal.technicaltask.utils.TestSchedulerProvider
import com.michal.technicaltask.utils.verifyCapturedArgumentsInOrder
import com.michal.technicaltask.utils.whenever
import com.michal.time.TimeFormatter
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeViewModelAddTest {

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
            creationDate = mockk(),
            creationRelativeTimeText = "",
        ),
        UserItem(
            id = 2,
            name = "test2",
            email = "email2",
            creationDate = mockk(),
            creationRelativeTimeText = "",
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
    private lateinit var refreshContentUseCase: RefreshContentUseCase

    @Mock
    private lateinit var userAdapterItemMapper: UserAdapterItemMapper

    @Mock
    private lateinit var timeFormatter: TimeFormatter

    @Mock
    private lateinit var testObserver: Observer<UsersViewState>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<UsersViewState>

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        whenever(userAdapterItemMapper.map(testUsers)).thenReturn(adapterItems)
        whenever(getAllUsersUseCase.execute()).thenReturn(Single.just(testUsers))
        viewModel = HomeViewModel(
            getAllUsersUseCase,
            addNewUserUseCase,
            removeUserUseCase,
            refreshContentUseCase,
            TestSchedulerProvider(),
            timeFormatter,
            userAdapterItemMapper,
        )
        viewModel.usersViewState.observeForever(testObserver)
    }

    @Test
    fun `Should emit loading and updated content state for adding a new user`() {
        // given
        val id = 100
        val name = "name"
        val email = "email"
        val newUser = User(
            id = id,
            name = name,
            email = email,
            creationDate = mockk()
        )
        val newAdapterItemUser = UserItem(
            id = id,
            name = name,
            email = email,
            creationDate = mockk(),
            creationRelativeTimeText = ""
        )

        whenever(addNewUserUseCase.execute(AddNewUserUseCase.Params(name, email))).thenReturn(Single.just(newUser))
        whenever(userAdapterItemMapper.mapUser(newUser)).thenReturn(newAdapterItemUser)

        // when
        viewModel.addNewUser(name, email)

        // then
        testObserver.verifyCapturedArgumentsInOrder(
            argumentCaptor,
            UsersViewState.Content(
                userItems = adapterItems
            ),
            UsersViewState.Loading(),
            UsersViewState.Content(
                userItems = adapterItems.toMutableList().plus(newAdapterItemUser)
            )
        )
    }

}