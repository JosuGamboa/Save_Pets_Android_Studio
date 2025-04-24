package com.example.save_pets

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val dbHelper = DBHelper(this)

        findViewById<Button>(R.id.btnRegistrarUsuario).setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
            val apellido = findViewById<EditText>(R.id.etApellido).text.toString()
            val telefono = findViewById<EditText>(R.id.etTelefono).text.toString()
            val contraseña = findViewById<EditText>(R.id.etContraseña).text.toString()
            val confirmar = findViewById<EditText>(R.id.etConfirmarContraseña).text.toString()

            val esTelefonoBolivia = Regex("^6\\d{7}|7\\d{7}").matches(telefono)

            if (nombre.isBlank() || apellido.isBlank() || telefono.isBlank() || contraseña.isBlank()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (!esTelefonoBolivia) {
                Toast.makeText(this, "Ingresa un teléfono válido de Bolivia", Toast.LENGTH_SHORT).show()
            } else if (contraseña != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                val registrado = dbHelper.registrarUsuario(nombre, apellido, telefono, contraseña)
                if (registrado) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish() // Volver al login
                } else {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}