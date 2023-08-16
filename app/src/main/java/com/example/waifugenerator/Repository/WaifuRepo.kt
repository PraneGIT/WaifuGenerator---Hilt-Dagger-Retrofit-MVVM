package com.example.waifugenerator.Repository

import com.example.waifugenerator.Retrofit.waifuAPI
import javax.inject.Inject

class WaifuRepo @Inject constructor(private val waifuAPI: waifuAPI) {

    suspend fun getWaifuURL(type:String)=waifuAPI.getWaifu(type)

}