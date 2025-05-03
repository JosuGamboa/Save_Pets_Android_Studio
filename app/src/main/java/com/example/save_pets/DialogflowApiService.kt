package com.example.save_pets

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface DialogflowApiService {
    @POST
    fun detectIntent(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: DialogflowRequestBody
    ): Call<DialogflowResponse>
}
