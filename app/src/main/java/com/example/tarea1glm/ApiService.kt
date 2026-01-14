package com.example.tarea1glm

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("exercises")
    suspend fun getEjercicios(
        @Query("muscle") muscle: String
    ): List<Exercise>
}
