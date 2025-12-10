package com.example.tarea1glm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tarea1glm.LoginActivity


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. Encontrar las vistas (el botón y el texto) por su ID
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val txtLogin = findViewById<TextView>(R.id.txtLogin)

        // 2. Crear la función para navegar a LoginActivity
        val irALogin = {
            // Se crea un Intent que especifica que queremos ir de esta actividad (this)
            // a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // 3. Asignar el click listener al botón "Crear cuenta"
        btnCrearCuenta.setOnClickListener {
            // Aquí iría tu lógica para registrar al usuario.
            // Por ahora, solo navegaremos a Login.

            // Llamamos a la función para ir a la pantalla de login
            irALogin()
        }

        // 4. Asignar el click listener al texto "Iniciar sesión"
        txtLogin.setOnClickListener {
            // Llamamos a la función para ir a la pantalla de login
            irALogin()
        }
    }
}


