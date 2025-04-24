package com.example.save_pets

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import android.view.ViewGroup


class VerMascotasActivity : AppCompatActivity() {

    private lateinit var listViewMascotas: ListView
    private lateinit var dbHelper: DBHelper
    private lateinit var mascotasList: List<Mascota>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_mascotas)

        listViewMascotas = findViewById(R.id.listViewMascotas)
        dbHelper = DBHelper(this)

        // Obtener todas las mascotas registradas
        mascotasList = dbHelper.obtenerMascotas()

        // Crear un adaptador personalizado para mostrar cada mascota con botones
        val adapter = MascotaAdapter(mascotasList)
        listViewMascotas.adapter = adapter

        // Establecer el comportamiento de los botones Editar y Eliminar
        listViewMascotas.setOnItemClickListener { parent, view, position, id ->
            val selectedMascota = mascotasList[position]
            // Mostrar un mensaje con el nombre de la mascota seleccionada
            Toast.makeText(this, "Seleccionaste: ${selectedMascota.nombre}", Toast.LENGTH_SHORT).show()
        }
    }

    // Adaptador personalizado para manejar la visualización y los botones de cada mascota
    inner class MascotaAdapter(private val mascotasList: List<Mascota>) : BaseAdapter() {

        override fun getCount(): Int {
            return mascotasList.size
        }

        override fun getItem(position: Int): Any {
            return mascotasList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.item_mascota, parent, false)

            val mascota = mascotasList[position]

            // Mostrar el nombre de la mascota en el TextView
            val mascotaNombre = view.findViewById<TextView>(R.id.mascotaNombre)
            mascotaNombre.text = mascota.nombre

            // Botón de editar
            val btnEditar = view.findViewById<Button>(R.id.btnEditar)
            btnEditar.setOnClickListener {
                // Lógica para editar la mascota
                val intent = Intent(this@VerMascotasActivity, EditarMascotaActivity::class.java)
                intent.putExtra("mascota_id", mascota.id) // Pasar el ID de la mascota a editar
                startActivity(intent)
            }

            // Botón de eliminar
            val btnEliminar = view.findViewById<Button>(R.id.btnEliminar)
            btnEliminar.setOnClickListener {
                // Mostrar un diálogo de confirmación antes de eliminar
                mostrarDialogoConfirmacion(mascota)
            }

            return view
        }
    }

    // Método para mostrar un cuadro de diálogo de confirmación
    private fun mostrarDialogoConfirmacion(mascota: Mascota) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro de que deseas eliminar la mascota ${mascota.nombre}?")
            .setCancelable(false)
            .setPositiveButton("Sí") { dialog, id ->
                // Eliminar la mascota
                val success = dbHelper.eliminarMascota(mascota.id)

                if (success) {
                    Toast.makeText(this, "Mascota eliminada", Toast.LENGTH_SHORT).show()
                    actualizarLista()  // Actualiza la lista de mascotas
                } else {
                    Toast.makeText(this, "Error al eliminar la mascota", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No") { dialog, id ->
                // No hacer nada si el usuario decide no eliminarla
                dialog.cancel()
            }

        builder.create().show()
    }

    // Método para actualizar la lista de mascotas después de eliminar o editar
    private fun actualizarLista() {
        mascotasList = dbHelper.obtenerMascotas()
        val adapter = MascotaAdapter(mascotasList)
        listViewMascotas.adapter = adapter
    }
}