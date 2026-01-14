package com.example.tarea1glm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MisRutinasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_rutinas)


        findViewById<ImageView>(R.id.closeButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        cargarEjercicios()
    }

    private fun cargarEjercicios() {

        val tipos = mapOf(
            "biceps" to findViewById<RecyclerView>(R.id.rvBiceps),
            "lower_back" to findViewById<RecyclerView>(R.id.rvEspalda),
            "chest" to findViewById<RecyclerView>(R.id.rvPecho),
            "shoulders" to findViewById<RecyclerView>(R.id.rvHombro),
            "quadriceps" to findViewById<RecyclerView>(R.id.rvPiernas)
        )

        lifecycleScope.launch {
            for ((musculo, recyclerView) in tipos) {
                try {
                    val lista = withContext(Dispatchers.IO) {
                        ApiClient.api.getEjercicios(musculo)
                    }

                    Log.d("API", "Ejercicios de $musculo: ${lista.size}")


                    recyclerView.layoutManager = LinearLayoutManager(
                        this@MisRutinasActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    recyclerView.adapter = ExerciseAdapter(lista)

                } catch (e: Exception) {
                    Log.e("API", "Error al cargar ejercicios de $musculo", e)
                }
            }
        }
    }
}
