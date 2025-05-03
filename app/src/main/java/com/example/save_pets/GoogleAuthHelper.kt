package com.example.save_pets

import android.util.Log
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class GoogleAuthHelper {

    suspend fun getAccessToken(): String? = withContext(Dispatchers.IO) {
        return@withContext try {
            val credentialsJson = """
                {
                  "type": "service_account",
                  "project_id": "savepetsbot-ulvg",
                  "private_key_id": "1a3e241b9c0874d7c3b2354a61106abcc97f3397",
                  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCKuQFmOVKakQjG\n...
                  "client_email": "dialogflow-service-account@savepetsbot-ulvg.iam.gserviceaccount.com",
                  "client_id": "102568728774498153128",
                  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
                  "token_uri": "https://oauth2.googleapis.com/token",
                  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
                  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/dialogflow-service-account%40savepetsbot-ulvg.iam.gserviceaccount.com"
                }
            """.trimIndent()

            val credentialsStream = ByteArrayInputStream(credentialsJson.toByteArray(StandardCharsets.UTF_8))
            val credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))

            credentials.refreshIfExpired()
            credentials.accessToken.tokenValue
        } catch (e: Exception) {
            Log.e("GoogleAuthHelper", "Error al obtener el token", e)
            null
        }
    }
}