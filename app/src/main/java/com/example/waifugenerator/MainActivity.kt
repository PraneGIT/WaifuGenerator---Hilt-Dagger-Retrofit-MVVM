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
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.waifugenerator.Contants.Constants
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel:MainViewModel
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
        findViewById<Button>(R.id.btn_main).setOnClickListener {

            GlobalScope.launch {
                mainViewModel.getWaifu(Constants.BASE_URL + spinner.selectedItem).join().let {
                    e("url2",Constants.BASE_URL + spinner.selectedItem)
                    if(mainViewModel.mWaifuLink.isNullOrEmpty()){
                        Log.e("url", "null")


                    }else{
                        runOnUiThread {
                            Glide
                                .with(this@MainActivity)
                                .load(mainViewModel.mWaifuLink)
                                .placeholder(com.bumptech.glide.R.drawable.abc_ab_share_pack_mtrl_alpha)
                                .centerCrop()
                                .into(findViewById<ImageView>(R.id.iv_waifu));
                        }

                        Log.e("url",mainViewModel.mWaifuLink!!)
                    }
                }
            }


        }

    }

    fun setWaifu(url:String){

    }
}