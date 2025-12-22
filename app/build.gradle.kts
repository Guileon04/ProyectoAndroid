plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services) // <-- AÑADIDO: Aplica el plugin de Google Services
}

android {
    namespace = "com.example.tarea1glm"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.tarea1glm"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    // Habilitamos ViewBinding, que es la forma moderna de usar XML sin findViewById
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Dependencias básicas para un proyecto con XML Views
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx) // Se usa .activity.ktx en lugar de .activity

    // 1. Se importa el Firebase BOM (Bill of Materials)
    // Él gestionará las versiones de todas las demás librerías de Firebase.
    implementation(platform(libs.firebase.bom))

    // 2. Se añaden las dependencias de Firebase SIN especificar la versión.
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth:19.1.0")
    implementation(libs.firebase.auth.ktx) // La librería que te daba error, ahora sin versión explícita

    // Dependencias de testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
