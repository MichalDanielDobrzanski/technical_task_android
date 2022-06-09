package com.michal.technicaltask.presentation.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.michal.technicaltask.R
import com.michal.technicaltask.data.FakeUsersRepository
import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.data.user.di.UsersModule
import com.michal.technicaltask.presentation.home.adapter.UsersAdapter
import com.michal.technicaltask.utils.launchFragmentInHiltContainer
import com.michal.technicaltask.utils.matchesTextOnRecyclerView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(UsersModule::class)
class HomeFragmentTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object FakeUsersModule {
        @Provides
        fun provideUsersRepository(): UsersRepository = FakeUsersRepository()
    }

    @Test
    fun testContent() {
        launchFragmentInHiltContainer<HomeFragment>()
        scrollAndAssertTextVisible(0, "test1")
        scrollAndAssertTextVisible(1, "test2")
        scrollAndAssertTextVisible(2, "test3")
    }

    @Test
    fun testNavigatingToAddUserDialog() {
        launchFragmentInHiltContainer<HomeFragment>()

        onView(withId(R.id.add_user_button)).perform(click())

        onView(withId(R.id.add_user_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigatingToRemoveUserDialog() {
        launchFragmentInHiltContainer<HomeFragment>()

        onView(withId(R.id.home_users_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UsersAdapter.ViewHolder>(0, longClick())
            )

        onView(withText(R.string.remove_user_text)).check(matches(isDisplayed()))
    }

    @Test
    fun testRemovingUser() {
        launchFragmentInHiltContainer<HomeFragment>()

        onView(withId(R.id.home_users_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UsersAdapter.ViewHolder>(0, longClick())
            )

        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click())

        onView(withId(R.id.home_users_recycler_view))
            .check(matches(not(matchesTextOnRecyclerView(R.id.user_name_text, "test1"))))
    }

    private fun scrollAndAssertTextVisible(position: Int, text: String) {
        onView(withId(R.id.home_users_recycler_view))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<UsersAdapter.ViewHolder>(position)
            )
        onView(withText(text)).check(matches(isDisplayed()))
    }
}