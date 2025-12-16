package com.example.tarea1glm // Reemplaza con tu paquete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Definición de la clase, una sola vez y de forma correcta
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. Referencias a las vistas
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val txtLogin = findViewById<TextView>(R.id.txtLogin)

        // 2. Listener para el botón "Crear Cuenta"
        btnCrearCuenta.setOnClickListener {
            // Simulamos un registro exitoso y navegamos a la pantalla principal
            val intent = Intent(this, MainActivity::class.java)

            // Limpiamos la pila de actividades para que el usuario no pueda volver atrás
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        // 3. Listener para el texto "Iniciar sesión"
        txtLogin.setOnClickListener {
            // Simplemente vuelve a la pantalla de login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

