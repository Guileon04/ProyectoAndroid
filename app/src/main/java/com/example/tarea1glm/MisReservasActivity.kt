package com.example.tarea1glm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MisReservasActivity : AppCompatActivity() {

    private lateinit var adView: AdView
    private val listaReservas = mutableListOf<Reserva>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mis_sesiones)

        findViewById<ImageView>(R.id.closeButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        adView = findViewById(R.id.anuncio)
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val recycler = findViewById<RecyclerView>(R.id.recyclerProfesionales)
        recycler.layoutManager = LinearLayoutManager(this)
        val adapter = ReservasAdapter(listaReservas)
        recycler.adapter = adapter

        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("usuarios")
            .document(user.uid)
            .collection("reservas")
            .get()
            .addOnSuccessListener { documentos ->
                listaReservas.clear()

                for (doc in documentos) {
                    val reserva = doc.toObject(Reserva::class.java)


                    db.collection("sesiones")
                        .document(reserva.idSesion)
                        .get()
                        .addOnSuccessListener { sesionDoc ->
                            val imagenSesion = sesionDoc.getString("imagen") ?: ""

                            val reservaFinal = reserva.copy(imagen = imagenSesion)
                            listaReservas.add(reservaFinal)
                            adapter.notifyDataSetChanged()
                        }
                }
            }
    }
}
