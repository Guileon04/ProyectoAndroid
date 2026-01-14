package com.example.tarea1glm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ReservasAdapter(
    private val lista: List<Reserva>
) : RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder>() {

    inner class ReservaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagen: ImageView = view.findViewById(R.id.imagen)
        val nombre: TextView = view.findViewById(R.id.nombre)
        val hora: TextView = view.findViewById(R.id.hora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recoger_sesiones, parent, false)
        return ReservaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = lista[position]

        holder.nombre.text = reserva.nombre
        holder.hora.text = reserva.hora

        Glide.with(holder.itemView.context)
            .load(reserva.imagen)
            .into(holder.imagen)
    }

    override fun getItemCount() = lista.size
}
