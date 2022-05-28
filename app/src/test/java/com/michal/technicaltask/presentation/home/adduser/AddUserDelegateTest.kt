package com.michal.technicaltask.presentation.home.adduser

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AddUserDelegateTest {

    private lateinit var delegate: AddUserDelegate

    @Before
    fun setup() {
        delegate = AddUserDelegate()
    }

    @Test
    fun `Should make null names invalid`() {
        val name = null

        val result = delegate.isValidName(name)

        assertFalse(result)
    }

    @Test
    fun `Should make empty names invalid`() {
        val name = ""

        val result = delegate.isValidName(name)

        assertFalse(result)
    }

    @Test
    fun `Should make non empty names valid`() {
        val name = "a"

        val result = delegate.isValidName(name)

        assertTrue(result)
    }

    @Test
    fun `Should make null emails invalid`() {
        val email = null

        val result = delegate.isValidEmail(email)

        assertFalse(result)
    }

    @Test
    fun `Should make empty emails invalid`() {
        val email = ""

        val result = delegate.isValidEmail(email)

        assertFalse(result)
    }

    @Test
    fun `Should make properly formatted emails valid`() {
        val name = "a@test.com"

        val result = delegate.isValidEmail(name)

        assertTrue(result)
    }

    @Test
    fun `Should call the name validator callback first`() {
        delegate.validateNameAndEmail(
            null,
            null,
            { assert(true) },
            { assert(false) }
        ) { _, _ -> assert(false) }
    }

    @Test
    fun `Should call the email validator callback when name is correct`() {
        delegate.validateNameAndEmail(
            "validName",
            null,
            { assert(false) },
            { assert(true) }
        ) { _, _ -> assert(false) }
    }

    @Test
    fun `Should call the success callback for valid name and email`() {
        delegate.validateNameAndEmail(
            "validName",
            "email@domain.com",
            { assert(false) },
            { assert(false) }
        ) { _, _ -> assert(true) }
    }
}