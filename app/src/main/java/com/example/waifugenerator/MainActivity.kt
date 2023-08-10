package com.example.waifugenerator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.waifugenerator.Contants.Constants
import com.example.waifugenerator.Retrofit.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel:MainViewModel
//        private val mainViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val types = resources.getStringArray(R.array.types)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, types
            )
            spinner.adapter = adapter
        }

        mainViewModel= ViewModelProvider(this)[MainViewModel::class.java]

        setWaifuObservables()

        findViewById<Button>(R.id.btn_main).setOnClickListener {
            mainViewModel.getWaifu(Constants.BASE_URL + spinner.selectedItem)
        }

    }

    fun setWaifuObservables(){
        mainViewModel.mWaifuLink.observe(this, Observer {resouce->
            when(resouce.status){
                Resource.Status.SUCCESS->{
                    Glide
                        .with(this@MainActivity)
                        .load(resouce.data)
                        .placeholder(com.bumptech.glide.R.drawable.abc_ab_share_pack_mtrl_alpha)
                        .centerCrop()
                        .into(findViewById<ImageView>(R.id.iv_waifu));
                }
                Resource.Status.ERROR->{
                    Log.e("url",resouce.message!!)
                }
                Resource.Status.LOADING->{
                    Log.e("url","loading")
                }

                else -> {
                    Log.e("url","else")
                }
            }

        })

    }
}