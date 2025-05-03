package com.example.save_pets.util

object Validator {
    fun isValidBolivianPhoneNumber(phone: String): Boolean {
        return phone.matches(Regex("^[67][0-9]{7}$"))
    }
}