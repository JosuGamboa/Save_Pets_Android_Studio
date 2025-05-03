package com.example.save_pets

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ChatbotActivity : AppCompatActivity() {

    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var chatLayout: LinearLayout
    private lateinit var scrollViewChat: ScrollView

    private lateinit var authHelper: GoogleAuthHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gfgPolicy =
            ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(gfgPolicy)
        setContentView(R.layout.activity_chatbot)

        // Inicializar vistas
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        chatLayout = findViewById(R.id.chatLayout)
        scrollViewChat = findViewById(R.id.scrollViewChat)

        // Inicializar el GoogleAuthHelper
        authHelper = GoogleAuthHelper()

        btnSend.setOnClickListener {
            val userMessage = etMessage.text.toString()
            if (userMessage.isNotEmpty()) {
                addUserMessage(userMessage)
                addBotMessage("Cargando respuesta...") // Mostramos mensaje mientras espera
                sendMessageToDialogflow(userMessage)
                etMessage.text.clear()
            }
        }
    }

    private fun sendMessageToDialogflow(message: String) {
        lifecycleScope.launch {
            // Obtener el access token en segundo plano
            val accessToken = authHelper.getAccessToken()

            if (accessToken.isNullOrEmpty()) {
                // Error obteniendo token
                updateLastBotMessage("Error: No se pudo obtener el token de autenticación.")
                Log.e("ChatbotActivity", "Token de autenticación nulo o vacío")
                return@launch
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("https://dialogflow.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(DialogflowApiService::class.java)

            val projectId = "savepetsbot-ulvg" // Asegúrate de que sea el correcto
            val sessionId = "session1234" // Puedes cambiarlo dinámicamente luego

            val url = "v2/projects/$projectId/agent/sessions/$sessionId:detectIntent"

            val body = DialogflowRequestBody(
                queryInput = QueryInput(
                    text = TextInput(
                        text = message,
                        languageCode = "es"
                    )
                )
            )

            val call = service.detectIntent(url, "Bearer $accessToken", body)

            call.enqueue(object : Callback<DialogflowResponse> {
                override fun onResponse(
                    call: Call<DialogflowResponse>,
                    response: Response<DialogflowResponse>
                ) {
                    if (response.isSuccessful) {
                        val fulfillmentText = response.body()?.queryResult?.fulfillmentText
                        Log.i("ChatbotActivity", "Respuesta del bot: $fulfillmentText")
                        updateLastBotMessage(fulfillmentText ?: "Sin respuesta del bot.")
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("ChatbotActivity", "Error en la respuesta: $errorBody")
                        updateLastBotMessage("Error al procesar la respuesta: $errorBody")
                    }
                }

                override fun onFailure(call: Call<DialogflowResponse>, t: Throwable) {
                    Log.e("ChatbotActivity", "Fallo en la red: ${t.localizedMessage}")
                    updateLastBotMessage("Error de red: ${t.localizedMessage}")
                }
            })
        }
    }

    public fun addUserMessage(message: String) {
        val textView = TextView(this)
        textView.text = "Tú: $message"
        chatLayout.addView(textView)
        scrollViewChat.post { scrollViewChat.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    public fun addBotMessage(message: String) {
        val textView = TextView(this)
        textView.text = "Bot: $message"
        textView.tag = "bot_message"
        chatLayout.addView(textView)
        scrollViewChat.post { scrollViewChat.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    public fun updateLastBotMessage(newMessage: String) {
        for (i in chatLayout.childCount - 1 downTo 0) {
            val view = chatLayout.getChildAt(i)
            if (view.tag == "bot_message" && view is TextView) {
                view.text = "Bot: $newMessage"
                break
            }
        }
        scrollViewChat.post { scrollViewChat.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}