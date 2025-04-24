package com.example.save_pets

data class Mascota(
    val id: Int,
    val nombre: String,
    val edad: Int,
    val raza: String,
    val sexo: String,
    val vacunado: String,
    val tipoVacunacion: String?,
    val fechaVacunacion: String?,
    val esterilizado: Int
)