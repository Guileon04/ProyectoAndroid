package com.example.tarea1glm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdView

class MiPerfilActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_perfil)

        auth = FirebaseAuth.getInstance()

        val tvNombreUsuario = findViewById<TextView>(R.id.tvNombreUsuario)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val closeButton = findViewById<ImageView>(R.id.closeButton)


        adView = findViewById(R.id.anuncio)
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        closeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val user = auth.currentUser
        tvNombreUsuario.text = user?.email ?: "Socio VivaGym"

        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun cerrarSesion() {
        auth.signOut()
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        prefs.edit().clear().apply()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
