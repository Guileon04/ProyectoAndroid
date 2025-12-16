// COPIA Y PEGA ESTE CONTENIDO COMPLETO

pluginManagement {
    repositories {google()
        mavenCentral() // Necesario para encontrar los plugins de Google y otros
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral() // El más importante, para encontrar las librerías de Firebase
    }
}

rootProject.name = "ProyectoAndroid" // Asegúrate de que el nombre sea el de tu proyecto
include(":app")
