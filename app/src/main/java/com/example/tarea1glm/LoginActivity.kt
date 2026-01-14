package com.example.tarea1glm

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        if (sesionIniciada()) {
            irAMain()
            return
        }

        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.edtEmailLogin)
        val etPassword = findViewById<EditText>(R.id.edtPassLogin)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtIrARegistro = findViewById<TextView>(R.id.txtIrARegistro)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                toast("Completa todos los campos")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        guardarSesion(email)
                        irAMain()
                    } else {
                        toast(task.exception?.message ?: "Error al iniciar sesión")
                    }
                }
        }

        txtIrARegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun guardarSesion(email: String) {
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        prefs.edit()
            .putBoolean("logueado", true)
            .putString("email", email)
            .apply()
    }

    private fun sesionIniciada(): Boolean {
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        return prefs.getBoolean("logueado", false)
    }

    private fun irAMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
