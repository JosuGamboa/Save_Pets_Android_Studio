package com.example.save_pets

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class OpinionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opinion)

        val rgTipoServicio: RadioGroup = findViewById(R.id.rgTipoServicio)
        val etNombreLugar: EditText = findViewById(R.id.etNombreLugar)
        val ratingBar: RatingBar = findViewById(R.id.ratingBar)
        val etOpinion: EditText = findViewById(R.id.etOpinion)
        val btnEnviarOpinion: Button = findViewById(R.id.btnEnviarOpinion)

        btnEnviarOpinion.setOnClickListener {
            val tipoServicio = when (rgTipoServicio.checkedRadioButtonId) {
                R.id.rbCentroBelleza -> "Centro de Belleza"
                R.id.rbGuarderia -> "Guardería"
                else -> "No especificado"
            }

            val nombreLugar = etNombreLugar.text.toString()
            val calificacion = ratingBar.rating
            val opinion = etOpinion.text.toString()

            if (nombreLugar.isEmpty() || opinion.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Opinión enviada sobre $tipoServicio\nLugar: $nombreLugar\nCalificación: $calificacion estrellas\nOpinión: $opinion",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}