package com.example.save_pets

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnRegistroMascota: Button
    private lateinit var btnCuidadoHigiene: Button
    private lateinit var btnAlimentacionNutricion: Button
    private lateinit var btnOpinion: Button
    private lateinit var btnVerMascotas: Button  // Nuevo botón
    private lateinit var btnMapaCentrosBelleza: Button  // Nuevo botón
    private lateinit var btnChatbot: Button  // Nuevo botón

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRegistroMascota = findViewById(R.id.btnRegistroMascota)
        btnCuidadoHigiene = findViewById(R.id.btnCuidadoHigiene)
        btnAlimentacionNutricion = findViewById(R.id.btnAlimentacionNutricion)
        btnOpinion = findViewById(R.id.btnOpinion)
        btnVerMascotas = findViewById(R.id.btnVerMascotas)  // Inicializa el nuevo botón
        btnMapaCentrosBelleza = findViewById(R.id.btnMapaCentrosBelleza)  // Inicializa el botón del mapa
        btnChatbot = findViewById(R.id.btnChatbot)  // Inicializa el botón del chatbot

        // Redirigir a la pantalla correspondiente
        btnRegistroMascota.setOnClickListener {
            startActivity(Intent(this, RegistroMascotaActivity::class.java))
        }

        btnCuidadoHigiene.setOnClickListener {
            startActivity(Intent(this, CuidadoHigieneActivity::class.java))
        }

        btnAlimentacionNutricion.setOnClickListener {
            startActivity(Intent(this, AlimentacionNutricionActivity::class.java))
        }

        btnOpinion.setOnClickListener {
            startActivity(Intent(this, OpinionActivity::class.java))
        }

        // Redirigir a la actividad de ver mascotas registradas
        btnVerMascotas.setOnClickListener {
            startActivity(Intent(this, VerMascotasActivity::class.java))
        }

        // Redirigir a la actividad de mapa de centros de belleza
        btnMapaCentrosBelleza.setOnClickListener {
            startActivity(Intent(this, MapaCentrosBellezaActivity::class.java))
        }

        // Redirigir a la actividad de chatbot
        btnChatbot.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java))
        }
    }
}