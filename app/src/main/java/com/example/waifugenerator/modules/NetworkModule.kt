package com.example.waifugenerator.modules

import android.provider.SyncStateContract
import com.example.waifugenerator.Contants.Constants
import com.example.waifugenerator.Retrofit.waifuAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesWaifuAPi(retrofit:Retrofit):waifuAPI{
        return retrofit.create(waifuAPI::class.java)
    }
}