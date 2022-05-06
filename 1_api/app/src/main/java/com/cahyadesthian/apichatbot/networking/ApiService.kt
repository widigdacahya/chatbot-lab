package com.cahyadesthian.apichatbot.networking

import com.cahyadesthian.apichatbot.model.BotResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    fun getBotResponse(
        @Url url: String
    ): Call<BotResponse>


}