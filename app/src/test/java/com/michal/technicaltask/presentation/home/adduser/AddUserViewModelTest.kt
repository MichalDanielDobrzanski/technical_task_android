package com.michal.technicaltask.presentation.home.adduser

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AddUserViewModelTest {

    private lateinit var viewModel: AddUserViewModel

    @Before
    fun setup() {
        viewModel = AddUserViewModel()
    }

    @Test
    fun `Should make null names invalid`() {
        val name = null

        val result = viewModel.isValidName(name)

        assertFalse(result)
    }

    @Test
    fun `Should make empty names invalid`() {
        val name = ""

        val result = viewModel.isValidName(name)

        assertFalse(result)
    }

    @Test
    fun `Should make non empty names valid`() {
        val name = "a"

        val result = viewModel.isValidName(name)

        assertTrue(result)
    }

    @Test
    fun `Should make null emails invalid`() {
        val email = null

        val result = viewModel.isValidEmail(email)

        assertFalse(result)
    }

    @Test
    fun `Should make empty emails invalid`() {
        val email = ""

        val result = viewModel.isValidEmail(email)

        assertFalse(result)
    }

    @Test
    fun `Should make properly formatted emails valid`() {
        val name = "a@test.com"

        val result = viewModel.isValidEmail(name)

        assertTrue(result)
    }

    @Test
    fun `Should call the name validator callback first`() {
        viewModel.validateNameAndEmail(
            null,
            null,
            { assert(true) },
            { assert(false) }
        ) { _, _ -> assert(false) }
    }

    @Test
    fun `Should call the email validator callback when name is correct`() {
        viewModel.validateNameAndEmail(
            "validName",
            null,
            { assert(false) },
            { assert(true) }
        ) { _, _ -> assert(false) }
    }


    @Test
    fun `Should call the success callback for valid name and email`() {
        viewModel.validateNameAndEmail(
            "validName",
            "email@domain.com",
            { assert(false) },
            { assert(false) }
        ) { _, _ -> assert(true) }
    }
}