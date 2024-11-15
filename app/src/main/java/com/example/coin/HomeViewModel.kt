package com.example.coin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coin.repository.CoinDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor( val repository: CoinDataRepository) : ViewModel() {


    fun getData()
    {
        viewModelScope.launch{
            repository.getData()
        }
    }

}