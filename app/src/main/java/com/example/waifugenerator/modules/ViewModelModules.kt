package com.example.waifugenerator.modules

import androidx.lifecycle.ViewModel
import com.example.waifugenerator.MainViewModel
import com.example.waifugenerator.Repository.WaifuRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@InstallIn(ViewModelComponent::class)
@Module
class ViewModelModules {

    @Provides
    fun providesMainViewModel(waifuRepo: WaifuRepo):ViewModel{
        return MainViewModel(waifuRepo)
    }

}