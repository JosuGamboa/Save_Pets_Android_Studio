package com.example.save_pets

// El cuerpo del request que enviamos a Dialogflow
data class DialogflowRequestBody(
    val queryInput: QueryInput
)

// Dentro del request, está el "queryInput"
data class QueryInput(
    val text: TextInput
)

// Dentro del queryInput, está el "text" (lo que queremos decirle al bot)
data class TextInput(
    val text: String,            // Ejemplo: "Hola, ¿cómo estás?"
    val languageCode: String     // Ejemplo: "es" para español
)