package com.example.tarea1glm // Reemplaza con tu paquete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Definición correcta y única de la clase
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. Referencias a las vistas
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtIrARegistro = findViewById<TextView>(R.id.txtIrARegistro)

        // 2. Listener para el botón "Iniciar Sesión"
        btnLogin.setOnClickListener {
            // Simulamos un login exitoso y navegamos a la pantalla principal
            val intent = Intent(this, MainActivity::class.java)

            // Limpiamos la pila de actividades para que el usuario no pueda volver atrás con el botón "atrás"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        // 3. Listener para el texto "Regístrate"
        txtIrARegistro.setOnClickListener {
            // Navega a la pantalla de registro
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
