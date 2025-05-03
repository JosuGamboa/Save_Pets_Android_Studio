package com.example.save_pets

// Cuando Dialogflow responde, nos manda este JSON que convertimos en esta clase
data class DialogflowResponse(
    val queryResult: QueryResult
)

// Dentro de queryResult, está el fulfillmentText (lo que el bot responde)
data class QueryResult(
    val fulfillmentText: String  // Ejemplo: "¡Hola! ¿En qué puedo ayudarte?"
)
