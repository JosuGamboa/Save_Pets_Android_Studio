package com.example.save_pets

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditarMascotaActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etEdad: EditText
    private lateinit var etRaza: EditText
    private lateinit var rgSexo: RadioGroup
    private lateinit var btnGuardarCambios: Button
    private lateinit var dbHelper: DBHelper

    private var mascotaId: Int = -1  // ID de la mascota que vamos a editar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)

        etNombre = findViewById(R.id.etNombre)
        etEdad = findViewById(R.id.etEdad)
        etRaza = findViewById(R.id.etRaza)
        rgSexo = findViewById(R.id.rgSexo)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)
        dbHelper = DBHelper(this)

        // Obtener el ID de la mascota desde el Intent
        mascotaId = intent.getIntExtra("mascota_id", -1)

        if (mascotaId != -1) {
            // Cargar los datos de la mascota
            cargarDatosMascota(mascotaId)
        }

        // Configurar el botón de "Guardar Cambios"
        btnGuardarCambios.setOnClickListener {
            // Obtener los datos editados
            val nombre = etNombre.text.toString()
            val edad = etEdad.text.toString().toIntOrNull() ?: 0
            val raza = etRaza.text.toString()
            val sexo = if (rgSexo.checkedRadioButtonId == R.id.rbMacho) "Macho" else "Hembra"

            // Guardar los cambios
            val success = dbHelper.editarMascota(mascotaId, nombre, edad, raza, sexo)

            if (success) {
                Toast.makeText(this, "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show()
                finish()  // Cerrar la actividad y regresar a la pantalla anterior
            } else {
                Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Cargar los datos de la mascota
    private fun cargarDatosMascota(id: Int) {
        val mascota = dbHelper.obtenerMascotaPorId(id)
        if (mascota != null) {
            etNombre.setText(mascota.nombre)
            etEdad.setText(mascota.edad.toString())
            etRaza.setText(mascota.raza)
            if (mascota.sexo == "Macho") {
                rgSexo.check(R.id.rbMacho)
            } else {
                rgSexo.check(R.id.rbHembra)
            }
        }
    }
}