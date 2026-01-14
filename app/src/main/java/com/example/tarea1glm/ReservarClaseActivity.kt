package com.example.tarea1glm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.LoadAdError

class ReservarClaseActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservar_clase)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


        MobileAds.initialize(this)
        cargarAnuncio()

        val closeButton = findViewById<ImageView>(R.id.closeButton)
        closeButton.setOnClickListener { finish() }

        findViewById<Button>(R.id.btnConfirmarReserva1).setOnClickListener {
            reservarClase("sesion4", "21:30 - 22:15")
        }
        findViewById<Button>(R.id.btnConfirmarReserva2).setOnClickListener {
            reservarClase("sesion3", "20:30 - 21:15")
        }
        findViewById<Button>(R.id.btnConfirmarReserva3).setOnClickListener {
            reservarClase("sesion2", "19:30 - 20:15")
        }
        findViewById<Button>(R.id.btnConfirmarReserva4).setOnClickListener {
            reservarClase("sesion1", "18:30 - 19:15")
        }
    }


    private fun cargarAnuncio() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }


    private fun mostrarAnuncio() {
        if (interstitialAd != null) {
            interstitialAd?.show(this)
            interstitialAd = null
            cargarAnuncio()
        }
    }

    private fun reservarClase(idSesion: String, hora: String) {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        val userReservasRef = db.collection("usuarios")
            .document(user.uid)
            .collection("reservas")

        userReservasRef
            .whereEqualTo("idSesion", idSesion)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    Toast.makeText(this, "Ya has reservado esta sesión", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                db.collection("sesiones")
                    .document(idSesion)
                    .get()
                    .addOnSuccessListener { doc ->
                        if (!doc.exists()) {
                            Toast.makeText(this, "Sesión no encontrada", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }

                        val nombre = doc.getString("nombre") ?: ""
                        val imagen = doc.getString("imagen") ?: ""

                        val reserva = hashMapOf(
                            "idSesion" to idSesion,
                            "nombre" to nombre,
                            "hora" to hora,
                            "imagen" to imagen
                        )

                        userReservasRef
                            .add(reserva)
                            .addOnSuccessListener {

                                Toast.makeText(
                                    this,
                                    "Reserva confirmada en $nombre",
                                    Toast.LENGTH_LONG
                                ).show()

                                // Mostrar notificación
                                mostrarNotificacion(nombre, hora)

                                // Mostrar anuncio intersticial
                                mostrarAnuncio()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error al guardar la reserva", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al leer la sesión", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al verificar reservas", Toast.LENGTH_SHORT).show()
            }
    }

    private fun mostrarNotificacion(nombre: String, hora: String) {
        val canalId = "reservas_canal"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalId,
                "Notificaciones de Reservas",
                NotificationManager.IMPORTANCE_HIGH
            )
            canal.description = "Se notifica cuando se hace una nueva reserva"
            val gestor = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            gestor.createNotificationChannel(canal)
        }

        val intent = Intent(this, MisReservasActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE else 0

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, flag)

        val textoNoti = "Clase: $nombre | Horario: $hora"

        val builder = NotificationCompat.Builder(this, canalId)
            .setSmallIcon(R.drawable.viva)
            .setContentTitle("Reserva confirmada")
            .setContentText(textoNoti)
            .setStyle(NotificationCompat.BigTextStyle().bigText(textoNoti))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this).notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}
