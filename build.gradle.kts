// COPIA Y PEGA ESTE CONTENIDO COMPLETO

// Los plugins se declaran aquí pero no se aplican (apply false).
// Esto los hace disponibles para los módulos internos como ':app'.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false // <-- AÑADIDO: El plugin de Google Services
}
