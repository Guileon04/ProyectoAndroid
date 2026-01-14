package com.example.tarea1glm

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SuplementosActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    private var count = 0
    private var creatina = 0
    private var proteina = 0
    private var bcaa = 0
    private var pre = 0
    private var glutamina = 0
    private var omega = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.suplementos)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        cargarSuplementos()


        findViewById<ImageView>(R.id.closeButton).setOnClickListener {
            guardarSuplementos()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    private fun cargarSuplementos() {
        val user = auth.currentUser ?: return

        db.collection("usuarios")
            .document(user.uid)
            .collection("suplementos")
            .document("cantidades")
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    creatina = doc.getLong("creatina")?.toInt() ?: 0
                    proteina = doc.getLong("proteina")?.toInt() ?: 0
                    bcaa = doc.getLong("bcaa")?.toInt() ?: 0
                    pre = doc.getLong("pre")?.toInt() ?: 0
                    glutamina = doc.getLong("glutamina")?.toInt() ?: 0
                    omega = doc.getLong("omega")?.toInt() ?: 0


                    findViewById<TextView>(R.id.cantidadCreatina).text = creatina.toString()
                    findViewById<TextView>(R.id.cantidadProteina).text = proteina.toString()
                    findViewById<TextView>(R.id.cantidadBcaa).text = bcaa.toString()
                    findViewById<TextView>(R.id.cantidadPre).text = pre.toString()
                    findViewById<TextView>(R.id.cantidadGlu).text = glutamina.toString()
                    findViewById<TextView>(R.id.cantidadOmega).text = omega.toString()

                    setupCounter(R.id.menosCreatina, R.id.masCreatina, R.id.cantidadCreatina) { creatina = it }
                    setupCounter(R.id.menosProte, R.id.masProteina, R.id.cantidadProteina) { proteina = it }
                    setupCounter(R.id.menosBcaa, R.id.masBcaa, R.id.cantidadBcaa) { bcaa = it }
                    setupCounter(R.id.menosPre, R.id.masPre, R.id.cantidadPre) { pre = it }
                    setupCounter(R.id.menosGlu, R.id.masGlu, R.id.cantidadGlu) { glutamina = it }
                    setupCounter(R.id.menosOmega, R.id.masOmega, R.id.cantidadOmega) { omega = it }
                }
            }
    }



    private fun guardarSuplementos() {
        val user = auth.currentUser ?: return

        val data = hashMapOf(
            "creatina" to creatina,
            "proteina" to proteina,
            "bcaa" to bcaa,
            "pre" to pre,
            "glutamina" to glutamina,
            "omega" to omega
        )

        db.collection("usuarios")
            .document(user.uid)
            .collection("suplementos")
            .document("cantidades")
            .set(data)
            .addOnSuccessListener {


                //Notificaciones
                val canalId = "suplementos_guardados_canal"
                val canalNombre = "Registro de Suplementos"
                val canalDescripcion = "Notifica cuando se guardan los suplementos"

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    val canal = android.app.NotificationChannel(
                        canalId,
                        canalNombre,
                        android.app.NotificationManager.IMPORTANCE_HIGH
                    ).apply {
                        description = canalDescripcion
                    }

                    val gestor =
                        getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
                    gestor.createNotificationChannel(canal)
                }


                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                val flag = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                    android.app.PendingIntent.FLAG_IMMUTABLE else 0

                val pendingIntent = android.app.PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    flag
                )


                val textoNoti =
                    "Creatina: $creatina | Proteína: $proteina | BCAA: $bcaa | Pre: $pre | Glutamina: $glutamina | Omega: $omega"

                val builder = androidx.core.app.NotificationCompat.Builder(this, canalId)
                    .setSmallIcon(R.drawable.viva)
                    .setContentTitle("Suplementos guardados")
                    .setContentText(textoNoti)
                    .setStyle(
                        androidx.core.app.NotificationCompat.BigTextStyle()
                            .bigText(textoNoti)
                    )
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)

                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU ||
                    checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
                    android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    androidx.core.app.NotificationManagerCompat.from(this)
                        .notify(System.currentTimeMillis().toInt(), builder.build())
                }

                Toast.makeText(this, "Suplementos guardados", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
    }


    private fun setupCounter(minusBtn: Int, plusBtn: Int, textView: Int, onChange: (Int) -> Unit) {
        val btnMinus = findViewById<ImageButton>(minusBtn)
        val btnPlus = findViewById<ImageButton>(plusBtn)
        val txtCantidad = findViewById<TextView>(textView)

        var count = txtCantidad.text.toString().toInt()

        val maxLimit = 15

        btnPlus.setOnClickListener {
            if (count < maxLimit) {
                count++
                txtCantidad.text = count.toString()
                onChange(count)
            }
        }

        btnMinus.setOnClickListener {
            if (count > 0) {
                count--
                txtCantidad.text = count.toString()
                onChange(count)
            }
        }
    }


}
