package com.example.tarea1glm

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
            }
        }


        val txtSaludo = findViewById<TextView>(R.id.txtSaludo)


        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {

            txtSaludo.setText("Hola, ${user.email}")
        } else {
            txtSaludo.setText("Hola, invitado")
        }


        val cardAccesoQR = findViewById<MaterialCardView>(R.id.cardAccesoQR)
        val btnReservarClase = findViewById<MaterialButton>(R.id.btnReservarClase)
        val btnMisRutinas = findViewById<MaterialButton>(R.id.btnMisRutinas)
        val btnMiPerfil = findViewById<MaterialButton>(R.id.btnMiPerfil)
        val btnMisReservas = findViewById<MaterialButton>(R.id.btnMisReservas)
        val btnSuplementos = findViewById<MaterialButton>(R.id.btnSuplementos)
        val btnPersonalTrainer = findViewById<MaterialButton>(R.id.btnPersonalTrainer)

        cardAccesoQR.setOnClickListener {
            val vistaPopup = layoutInflater.inflate(R.layout.ver_qr, null)
            val dialogo = androidx.appcompat.app.AlertDialog.Builder(this)
                .setView(vistaPopup)
                .setCancelable(true)
                .create()
            dialogo.show()
        }

        btnReservarClase.setOnClickListener {
            startActivity(Intent(this, ReservarClaseActivity::class.java))
        }

        btnMisRutinas.setOnClickListener {
            startActivity(Intent(this, MisRutinasActivity::class.java))
        }

        btnPersonalTrainer.setOnClickListener {
            startActivity(Intent(this, MiPerfilActivity::class.java))
        }

        btnMisReservas.setOnClickListener {
            startActivity(Intent(this, MisReservasActivity::class.java))
        }

        btnSuplementos.setOnClickListener {
            startActivity(Intent(this, SuplementosActivity::class.java))
        }

        btnMiPerfil.setOnClickListener {
            startActivity(Intent(this, PersonalTrainerActivity::class.java))
        }
    }
}