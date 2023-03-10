package com.example.waifugenerator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waifugenerator.Repository.WaifuRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val waifuRepo: WaifuRepo) :ViewModel() {

     var mWaifuLink:String ?=null

   suspend fun getWaifu(url:String)=viewModelScope.launch {

        mWaifuLink= waifuRepo.getWaifuURL(url)

    }

}

