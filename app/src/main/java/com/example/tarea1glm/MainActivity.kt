package com.example.tarea1glm // Reemplaza con tu paquete

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

// Definición correcta de la clase
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Referencias a las vistas interactivas
        val cardAccesoQR = findViewById<MaterialCardView>(R.id.cardAccesoQR)
        val btnReservarClase = findViewById<MaterialButton>(R.id.btnReservarClase)
        val btnMisRutinas = findViewById<MaterialButton>(R.id.btnMisRutinas)
        val btnMiPerfil = findViewById<MaterialButton>(R.id.btnMiPerfil)

        // 2. Listener para la tarjeta de acceso QR
        cardAccesoQR.setOnClickListener {
            // Mostramos un mensaje temporal. En el futuro, podría abrir un QR más grande.
            Toast.makeText(this, "Mostrando código QR...", Toast.LENGTH_SHORT).show()
        }

        // 3. Listener para el botón "Reservar Clase"
        btnReservarClase.setOnClickListener {
            val intent = Intent(this, ReservarClaseActivity::class.java)
            startActivity(intent)
        }

        // 4. Listener para el botón "Mis Rutinas"
        btnMisRutinas.setOnClickListener {
            val intent = Intent(this, MisRutinasActivity::class.java)
            startActivity(intent)
        }

        // 5. Listener para el botón "Mi Perfil"
        btnMiPerfil.setOnClickListener {
            val intent = Intent(this, MiPerfilActivity::class.java)
            startActivity(intent)
        }
    }
}


