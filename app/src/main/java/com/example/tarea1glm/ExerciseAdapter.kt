package com.example.tarea1glm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private val exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.nombre)
        val tvTipo: TextView = view.findViewById(R.id.tipo)
        val estrella1: ImageView = view.findViewById(R.id.estrella1)
        val estrella2: ImageView = view.findViewById(R.id.estrella2)
        val estrella3: ImageView = view.findViewById(R.id.estrella3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recoger_de_api, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = exercises.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]

        holder.tvNombre.text = exercise.name
        holder.tvTipo.text = "Tipo: ${exercise.type}"


        holder.estrella1.setColorFilter(holder.estrella1.context.getColor(R.color.gris))
        holder.estrella2.setColorFilter(holder.estrella2.context.getColor(R.color.gris))
        holder.estrella3.setColorFilter(holder.estrella3.context.getColor(R.color.gris))


        when (exercise.difficulty.lowercase()) {
            "beginner" -> holder.estrella1.setColorFilter(holder.estrella1.context.getColor(R.color.amarillo))
            "intermediate" -> {
                holder.estrella1.setColorFilter(holder.estrella1.context.getColor(R.color.amarillo))
                holder.estrella2.setColorFilter(holder.estrella2.context.getColor(R.color.amarillo))
            }
            "advanced" -> {
                holder.estrella1.setColorFilter(holder.estrella1.context.getColor(R.color.amarillo))
                holder.estrella2.setColorFilter(holder.estrella2.context.getColor(R.color.amarillo))
                holder.estrella3.setColorFilter(holder.estrella3.context.getColor(R.color.amarillo))
            }
        }
    }


}
