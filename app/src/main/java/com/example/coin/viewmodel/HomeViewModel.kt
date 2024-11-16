package com.example.coin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    var _coinList: MutableLiveData<List<BaseCoinModel>> = MutableLiveData<List<BaseCoinModel>>()
    var coinList: LiveData<List<BaseCoinModel>> = _coinList


    @OptIn(ExperimentalCoroutinesApi::class)
    val screenState: StateFlow<ScreenState> = repository.getData().transformLatest {
        when (it) {
            is Result.ApiSuccess -> emit(SuccessData(it.data.toList()))
            Result.ApiError -> emit(ScreenState.Error("Error"))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000), ScreenState.Loading)

    var backSearchMode = false


    fun getData() {
        viewModelScope.launch {
            repository.getData()
        }
    }


    private fun setBackMode(backMode: Boolean) {
        this.backSearchMode = backMode
    }




    fun returnAllList() = (screenState.value as SuccessData).data


    fun processEvent(uiEvent: UIEvent) {
        when (uiEvent) {
            is UIEvent.BackToOriginal -> {
                _coinList.value = (screenState.value as SuccessData).data
                setBackMode(false)
            }

            is UIEvent.FilterResult -> {
                _coinList.value = (screenState.value as SuccessData).data.filter {
                    if (uiEvent.listOfFilter[0].isEmpty())
                        true
                    else
                        uiEvent.listOfFilter[0].isNotEmpty() && uiEvent.listOfFilter[0].contains(it.isActive.toString())

                }.filter {
                    if (uiEvent.listOfFilter[2].isEmpty())
                        true
                    else
                        uiEvent.listOfFilter[2].isNotEmpty() && uiEvent.listOfFilter[2].contains(it.isNew.toString())

                }.filter {
                    if (uiEvent.listOfFilter[1].isEmpty())
                        true
                    else
                        uiEvent.listOfFilter[1].isNotEmpty() && uiEvent.listOfFilter[1].contains(it.type)
                }
            }

            is UIEvent.SearchData -> {
                setBackMode(true)
                _coinList.value = (screenState.value as SuccessData).data.filter {
                    it.name?.equals(uiEvent.string, true) == true ||
                            it.symbol?.equals(uiEvent.string, true) == true ||
                            it.symbol?.contains(uiEvent.string, true) == true ||
                            it.name?.contains(uiEvent.string, true) == true
                }
            }
        }

    }

    fun syncData() {
        _coinList.value = (screenState.value as SuccessData).data
    }
}


sealed class ScreenState()
{
    object Loading: ScreenState()
    data class SuccessData(val data: List<BaseCoinModel>): ScreenState()
    data class Error(val error: String): ScreenState()

}


sealed class UIEvent() {
    data class SearchData(val string: String) : UIEvent()
    object BackToOriginal : UIEvent()
    data class FilterResult(val listOfFilter: MutableList<MutableList<String>>) : UIEvent()
}