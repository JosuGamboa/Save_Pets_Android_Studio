plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.save_pets"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.save_pets"
        minSdk = 24
        targetSdk = 35
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

    // packagingOptions debe ir dentro de la configuración de android
    packagingOptions {
        exclude ("META-INF/INDEX.LIST")
        exclude ("META-INF/gradle/incremental.annotation.processors")
        exclude ("META-INF/LICENSE")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/NOTICE.txt")
        exclude ("META-INF/DEPENDENCIES")  // Agregar esta línea
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation("com.android.volley:volley:1.2.1")

    testImplementation(libs.junit)

    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Librería para Google Sign-In (si usas esta para manejar la autenticación)
    implementation ("com.google.android.gms:play-services-auth:19.0.0")
    // Para Google Credentials
    implementation("com.google.auth:google-auth-library-oauth2-http:1.17.0")
// Para autenticación de Google
    implementation("com.google.auth:google-auth-library-oauth2-http:1.17.0")

// Para el cliente HTTP que usa GoogleAuthHelper
    implementation("com.google.api-client:google-api-client:1.32.1")
    implementation("com.google.http-client:google-http-client-gson:1.40.1")
    // Transporte HTTP basado en Apache (necesario para NetHttpTransport)
    implementation("com.google.http-client:google-http-client-apache-v2:1.40.1")

    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}