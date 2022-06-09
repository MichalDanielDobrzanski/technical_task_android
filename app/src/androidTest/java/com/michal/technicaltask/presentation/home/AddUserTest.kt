package com.michal.technicaltask.presentation.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.michal.technicaltask.R
import com.michal.technicaltask.data.FakeUsersRepository
import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.data.user.di.UsersModule
import com.michal.technicaltask.utils.launchFragmentInHiltContainer
import com.michal.technicaltask.utils.matchesTextOnRecyclerView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * This tests requires disabled animations.
 * Ref: https://developer.android.com/training/testing/espresso/setup#set-up-environment
 */
@HiltAndroidTest
@UninstallModules(UsersModule::class)
class AddUserTest {

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
    fun testAddingUser() {
        launchFragmentInHiltContainer<HomeFragment>()

        Espresso.onView(withId(R.id.add_user_button)).perform(click())
        Espresso.onView(withId(R.id.add_user_layout)).check(matches(isDisplayed()))

        val userName = "test4"
        Espresso.onView(withId(R.id.add_user_name_edit_text)).perform(typeText(userName))
        val userEmail = "test4@email.com"
        Espresso.onView(withId(R.id.add_user_email_edit_text)).perform(typeText(userEmail))
            .perform(closeSoftKeyboard())

        Espresso.onView(withText("OK"))
            .inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.home_users_recycler_view))
            .check(matches(matchesTextOnRecyclerView(R.id.user_name_text, "test1")))
    }
}