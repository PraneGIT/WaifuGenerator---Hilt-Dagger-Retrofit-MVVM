package com.example.waifugenerator.Retrofit

import com.example.waifugenerator.models.Waifu
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface waifuAPI {
    @GET
    suspend fun getWaifu(
        @Url url:String
    ):Response<Waifu>
}