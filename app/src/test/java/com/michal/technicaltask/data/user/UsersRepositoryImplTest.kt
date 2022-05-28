package com.michal.technicaltask.data.user

import com.michal.technicaltask.data.network.retrofit.RetrofitResponseMapper
import com.michal.technicaltask.data.network.retrofit.model.HttpApiException
import com.michal.technicaltask.data.user.model.AddNewUserRequestData
import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.utils.toFailedResponseMock
import com.michal.technicaltask.utils.toSuccessfulResponseMock
import com.michal.technicaltask.utils.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response

class UsersRepositoryImplTest {

    private val testUserData = UserData(
        id = 1,
        name = "name",
        email = "email"
    )

    @Mock
    private lateinit var usersApiService: UsersApiService

    @Mock
    private lateinit var retrofitResponseMapper: RetrofitResponseMapper

    private lateinit var usersRepository: UsersRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        usersRepository = UsersRepositoryImpl(
            usersApiService,
            retrofitResponseMapper
        )
    }

    @Test
    fun `Should get all users`() {
        // given
        val userDataList = listOf(
            testUserData,
            UserData(
                id = 2,
                name = "name",
                email = "email"
            )
        )
        val response = userDataList.toSuccessfulResponseMock()
        whenever(usersApiService.getAllUsers()).thenReturn(Single.just(response))
        whenever(retrofitResponseMapper.mapHttpResponse(response)).thenReturn(userDataList)

        // when
        val testObserver = usersRepository.getAllUsers().test()

        // then
        testObserver.assertValue(userDataList)
        testObserver.assertComplete()

        verify(usersApiService).getAllUsers()
    }

    @Test
    fun `Should emit error for all users for failed response`() {
        // given
        val userDataList = listOf(
            testUserData,
            UserData(
                id = 2,
                name = "name",
                email = "email"
            )
        )

        val failedResponse = userDataList.toFailedResponseMock()
        whenever(usersApiService.getAllUsers()).thenReturn(Single.just(failedResponse))

        val error = HttpApiException(errorCode = 404, message = null)
        whenever(retrofitResponseMapper.mapHttpResponse(failedResponse)).thenThrow(error)

        // when
        val testObserver = usersRepository.getAllUsers().test()

        // then
        testObserver.assertError(error)
        testObserver.assertNotComplete()

        verify(usersApiService).getAllUsers()
    }

    @Test
    fun `Should add new user`() {
        // given
        val name = "dummyName"
        val email = "dummyEmail"
        val addNewUserRequestData = AddNewUserRequestData(
            name = name,
            email = email,
            gender = "male",
            status = "active"
        )

        val response: Response<UserData> = testUserData.toSuccessfulResponseMock()
        whenever(usersApiService.addNewUser(addNewUserRequestData)).thenReturn(Single.just(response))
        whenever(retrofitResponseMapper.mapHttpResponse(response)).thenReturn(testUserData)

        // when
        val testObserver = usersRepository.addNewUser(
            name = name,
            email = email
        ).test()

        // then
        testObserver.assertValue(testUserData)
        testObserver.assertComplete()

        verify(usersApiService).addNewUser(addNewUserRequestData)
    }

    @Test
    fun `Should emit error for adding a new user for failed response`() {
        // given
        val name = "dummyName"
        val email = "dummyEmail"
        val addNewUserRequestData = AddNewUserRequestData(
            name = name,
            email = email,
            gender = "male",
            status = "active"
        )

        val failedResponse = testUserData.toFailedResponseMock()
        whenever(usersApiService.addNewUser(addNewUserRequestData)).thenReturn(Single.just(failedResponse))

        val error = HttpApiException(errorCode = 404, message = null)
        whenever(retrofitResponseMapper.mapHttpResponse(failedResponse)).thenThrow(error)

        // when
        val testObserver = usersRepository.addNewUser(
            name = name,
            email = email
        ).test()

        // then
        testObserver.assertError(error)
        testObserver.assertNotComplete()

        verify(usersApiService).addNewUser(addNewUserRequestData)
    }

    @Test(expected = NullPointerException::class)
    fun `Should use fallbacks for gender and status`() {
        // given
        val name = "dummyName"
        val email = "dummyEmail"
        val addNewUserRequestData = AddNewUserRequestData(
            name = name,
            email = email,
            gender = "gender",
            status = "status"
        )

        val failedResponse = testUserData.toFailedResponseMock()
        whenever(usersApiService.addNewUser(addNewUserRequestData)).thenReturn(Single.just(failedResponse))

        // when
        usersRepository.addNewUser(
            name = name,
            email = email
        )

        // then
        // expect error
    }

    @Test
    fun `Should remove user`() {
        // given
        val userId = 123

        val response = Unit.toSuccessfulResponseMock()
        whenever(usersApiService.removeUser(userId)).thenReturn(Single.just(response))

        // when
        val testObserver = usersRepository.removeUser(userId).test()

        // then
        testObserver.assertComplete()

        verify(usersApiService).removeUser(userId)
    }

    @Test
    fun `Should emit error for removing the user for failed response`() {
        // given
        val userId = 123

        val failedResponse = Unit.toFailedResponseMock()
        whenever(usersApiService.removeUser(userId)).thenReturn(Single.just(failedResponse))

        val error = HttpApiException(errorCode = 404, message = null)
        whenever(retrofitResponseMapper.mapEmptyHttpResponse(failedResponse)).thenThrow(error)

        // when
        val testObserver = usersRepository.removeUser(userId).test()

        // then
        testObserver.assertNotComplete()

        verify(usersApiService).removeUser(userId)
    }
}