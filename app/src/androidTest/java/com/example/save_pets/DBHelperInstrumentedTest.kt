package com.example.save_pets

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DBHelperInstrumentedTest {

    private lateinit var dbHelper: DBHelper

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        dbHelper = DBHelper(context)
    }

    @Test
    fun testRegistroUsuarioExitoso() {
        val resultado = dbHelper.registrarUsuario("Juan", "Perez", "71234567", "1234")
        assertTrue("El usuario debería registrarse correctamente", resultado)
    }

    @Test
    fun testVerificarUsuarioExistente() {
        dbHelper.registrarUsuario("Ana", "Lopez", "78945612", "abcd")
        val resultado = dbHelper.verificarUsuario("Ana", "Lopez", "abcd")
        assertTrue("El usuario debería existir", resultado)
    }

    @Test
    fun testVerificarUsuarioInexistente() {
        val resultado = dbHelper.verificarUsuario("Pedro", "Gomez", "pass123")
        assertFalse("El usuario NO debería existir", resultado)
    }
}