package com.example.save_pets

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(context: Context) : SQLiteOpenHelper(context, "save_pets.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            CREATE TABLE mascotas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                edad INTEGER NOT NULL,
                raza TEXT NOT NULL,
                sexo TEXT NOT NULL,
                vacunado TEXT NOT NULL,
                tipo_vacunacion TEXT,
                fecha_vacunacion TEXT,
                esterilizado INTEGER NOT NULL
            )
            """.trimIndent()
        )
        db?.execSQL(
            """
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellido TEXT NOT NULL,
                telefono TEXT NOT NULL,
                contraseña TEXT NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS mascotas")
        onCreate(db)
    }

    fun insertarMascota(
        nombre: String,
        edad: Int,
        raza: String,
        sexo: String,
        vacunado: String,
        tipoVacunacion: String?,
        fechaVacunacion: String?,
        esterilizado: Int,
        context: Context
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("edad", edad)
            put("raza", raza)
            put("sexo", sexo)
            put("vacunado", vacunado)
            put("tipo_vacunacion", tipoVacunacion)
            put("fecha_vacunacion", fechaVacunacion)
            put("esterilizado", esterilizado)
        }

        val result = db.insert("mascotas", null, values)
        if (result == -1L) {
            Toast.makeText(context, "No se pudo registrar", Toast.LENGTH_LONG).show()
            return false
        } else {
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
            return true
        }
    }

    fun editarMascota(id: Int, nombre: String, edad: Int, raza: String, sexo: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("nombre", nombre)
            put("edad", edad)
            put("raza", raza)
            put("sexo", sexo)
        }

        val result = db.update("mascotas", contentValues, "id = ?", arrayOf(id.toString()))
        return result > 0
    }

    fun obtenerMascotaPorId(id: Int): Mascota? {
        val db = this.readableDatabase
        val cursor = db.query(
            "mascotas",
            null,
            "id = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        var mascota: Mascota? = null
        if (cursor.moveToFirst()) {
            mascota = Mascota(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                raza = cursor.getString(cursor.getColumnIndexOrThrow("raza")),
                sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo")),
                vacunado = cursor.getString(cursor.getColumnIndexOrThrow("vacunado")),
                tipoVacunacion = cursor.getString(cursor.getColumnIndexOrThrow("tipo_vacunacion")),
                fechaVacunacion = cursor.getString(cursor.getColumnIndexOrThrow("fecha_vacunacion")),
                esterilizado = cursor.getInt(cursor.getColumnIndexOrThrow("esterilizado"))
            )
        }

        cursor.close()
        return mascota
    }

    fun obtenerMascotas(): List<Mascota> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM mascotas", null)
        val mascotasList = mutableListOf<Mascota>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                val raza = cursor.getString(cursor.getColumnIndexOrThrow("raza"))
                val sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo"))
                val vacunado = cursor.getString(cursor.getColumnIndexOrThrow("vacunado"))
                val tipoVacunacion = cursor.getString(cursor.getColumnIndexOrThrow("tipo_vacunacion"))
                val fechaVacunacion = cursor.getString(cursor.getColumnIndexOrThrow("fecha_vacunacion"))
                val esterilizado = cursor.getInt(cursor.getColumnIndexOrThrow("esterilizado"))

                val mascota = Mascota(
                    id, nombre, edad, raza, sexo, vacunado,
                    tipoVacunacion, fechaVacunacion, esterilizado
                )
                mascotasList.add(mascota)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return mascotasList
    }

    fun eliminarMascota(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete("mascotas", "id = ?", arrayOf(id.toString()))
        return result > 0
    }
    fun registrarUsuario(nombre: String, apellido: String, telefono: String, contraseña: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("apellido", apellido)
            put("telefono", telefono)
            put("contraseña", contraseña)
        }
        val resultado = db.insert("usuarios", null, values)
        return resultado != -1L
    }

    fun verificarUsuario(nombre: String, apellido: String, contraseña: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE nombre=? AND apellido=? AND contraseña=?",
            arrayOf(nombre, apellido, contraseña)
        )
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }

}