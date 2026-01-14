package com.example.tarea1glm

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.edtEmail)
        val etPassword = findViewById<EditText>(R.id.edtPass)
        val etConfirmPassword = findViewById<EditText>(R.id.edtPass2)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val txtLogin = findViewById<TextView>(R.id.txtLogin)

        btnCrearCuenta.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            when {
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ->
                    toast("Completa todos los campos")

                password.length < 6 ->
                    toast("La contraseña debe tener al menos 6 caracteres")

                password != confirmPassword ->
                    toast("Las contraseñas no coinciden")

                else -> {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                auth.signOut()
                                irALogin()
                            } else {
                                toast(task.exception?.message ?: "Error al registrar")
                            }
                        }
                }
            }
        }

        txtLogin.setOnClickListener {
            irALogin()
        }
    }

    private fun irALogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
