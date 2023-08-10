package com.example.waifugenerator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waifugenerator.Repository.WaifuRepo
import com.example.waifugenerator.Retrofit.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val waifuRepo: WaifuRepo) :ViewModel() {

    private var _mWaifuLink= MutableLiveData<Resource<String>>()
     var mWaifuLink : LiveData<Resource<String>> = _mWaifuLink

    fun getWaifu(url:String)=viewModelScope.launch {
        try {
            _mWaifuLink.value = Resource.loading()
            val response = waifuRepo.getWaifuURL(url)
            if(response.isSuccessful){
//                Log.d("MainViewModel", "getWaifu: ${response.body()!!.url}") //don't use logs in testing (pls)
                _mWaifuLink.value = Resource.success(response.body()!!.url)
            }else{
                throw Exception("Error getting waifu")
            }
        }catch (e:Exception){
            _mWaifuLink.value = Resource.error(e)
        }
    }

}

