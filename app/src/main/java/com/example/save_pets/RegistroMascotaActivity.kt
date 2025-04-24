package com.example.save_pets

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegistroMascotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_mascota)

        val rgVacunado: RadioGroup = findViewById(R.id.rgVacunado)
        val etTipoVacunacion: EditText = findViewById(R.id.etTipoVacunacion)
        val etFechaVacunacion: EditText = findViewById(R.id.etFechaVacunacion)

        rgVacunado.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbSi) {
                etTipoVacunacion.visibility = View.VISIBLE
                etFechaVacunacion.visibility = View.VISIBLE
            } else {
                etTipoVacunacion.visibility = View.GONE
                etFechaVacunacion.visibility = View.GONE
            }
        }

        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener {
            registrarMascota()
        }
    }

    private fun registrarMascota() {
        val dbHelper = DBHelper(this)

        val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
        val edadTexto = findViewById<EditText>(R.id.etEdad).text.toString()
        val raza = findViewById<EditText>(R.id.etRaza).text.toString()
        val sexo = if (findViewById<RadioButton>(R.id.rbMacho).isChecked) "Macho" else "Hembra"
        val vacunado = if (findViewById<RadioButton>(R.id.rbSi).isChecked) "Sí" else "No"
        val tipoVacunacion = findViewById<EditText>(R.id.etTipoVacunacion).text.toString()
        val fechaVacunacion = findViewById<EditText>(R.id.etFechaVacunacion).text.toString()
        val esterilizado = if (findViewById<CheckBox>(R.id.cbEsterilizado).isChecked) 1 else 0

        if (nombre.isEmpty() || edadTexto.isEmpty() || raza.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_LONG).show()
            return
        }

        val edad = edadTexto.toIntOrNull()
        if (edad == null) {
            Toast.makeText(this, "Edad inválida", Toast.LENGTH_LONG).show()
            return
        }

        dbHelper.insertarMascota(
            nombre,
            edad,
            raza,
            sexo,
            vacunado,
            if (vacunado == "Sí") tipoVacunacion else null,
            if (vacunado == "Sí") fechaVacunacion else null,
            esterilizado,
            this
        )
    }
}
