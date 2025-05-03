package com.example.save_pets

import com.example.save_pets.util.Validator
import org.junit.Assert.*
import org.junit.Test

class ValidatorTest {

    @Test
    fun validPhoneNumber_returnsTrue() {
        assertTrue(Validator.isValidBolivianPhoneNumber("71234567"))
    }

    @Test
    fun invalidPhoneNumber_returnsFalse() {
        assertFalse(Validator.isValidBolivianPhoneNumber("81234567"))
    }

    @Test
    fun shortPhoneNumber_returnsFalse() {
        assertFalse(Validator.isValidBolivianPhoneNumber("71234"))
    }
}