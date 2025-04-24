package com.example.save_pets

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val dbHelper = DBHelper(this)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvIrRegistro = findViewById<TextView>(R.id.tvIrRegistro)

        btnLogin.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombreLogin).text.toString()
            val apellido = findViewById<EditText>(R.id.etApellidoLogin).text.toString()
            val contraseña = findViewById<EditText>(R.id.etContraseñaLogin).text.toString()

            if (dbHelper.verificarUsuario(nombre, apellido, contraseña)) {
                Toast.makeText(this, "Bienvenido $nombre", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        tvIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}
