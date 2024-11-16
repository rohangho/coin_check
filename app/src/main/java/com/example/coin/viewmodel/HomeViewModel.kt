package com.example.coin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coin.model.BaseCoinModel
import com.example.coin.repository.CoinDataRepository
import com.example.coin.usecase.Result
import com.example.coin.viewmodel.ScreenState.SuccessData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor( val repository: CoinDataRepository) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val screenState : StateFlow<ScreenState> = repository.getData().transformLatest {
        when(it)
        {
            is Result.ApiSuccess -> emit(SuccessData(it.data.toList()))
            Result.ApiError -> emit(ScreenState.Error("Error"))
        }
    }.stateIn(viewModelScope,  SharingStarted.WhileSubscribed(2000), ScreenState.Loading)

    fun getData()
    {
        viewModelScope.launch{
            repository.getData()
        }
    }



    fun updateList(listOfFilter: MutableList<MutableList<String>>) =
        (screenState.value as SuccessData).data.filter {
            if(listOfFilter[0].isEmpty())
                true
            else
                listOfFilter[0].isNotEmpty() && listOfFilter[0].contains(it.isActive.toString())

        }.filter {
            if(listOfFilter[2].isEmpty())
                true
            else
                listOfFilter[2].isNotEmpty() && listOfFilter[2].contains(it.isNew.toString())

        }.filter {
            if(listOfFilter[1].isEmpty())
                true
            else
                listOfFilter[1].isNotEmpty() && listOfFilter[1].contains(it.type)
        }
    }



sealed class ScreenState()
{
    object Loading: ScreenState()
    data class SuccessData(val data: List<BaseCoinModel>): ScreenState()
    data class Error(val error: String): ScreenState()

}