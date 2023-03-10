package com.example.waifugenerator.Repository

import com.example.waifugenerator.Retrofit.waifuAPI
import javax.inject.Inject

class WaifuRepo @Inject constructor(private val waifuAPI: waifuAPI) {

    var waifuURL:String?=null
    suspend fun getWaifuURL(type:String):String?{
        val result =waifuAPI.getWaifu(type)
        if(result.isSuccessful && result.body()!=null){
            waifuURL=result.body()?.url.toString()
        }
        return waifuURL
    }

}