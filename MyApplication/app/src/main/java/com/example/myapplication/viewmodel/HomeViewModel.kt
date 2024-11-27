package com.example.coin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coin.repository.ProductRepository
import com.example.coin.usecase.Result
import com.example.coin.viewmodel.ScreenState.SuccessData
import com.example.example.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: ProductRepository) : ViewModel() {

    var _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState


    fun getData() {

        viewModelScope.launch {
            _screenState.emit(ScreenState.Loading)
            (repository.getData().collectLatest {
                when (it) {
                    Result.ApiError -> _screenState.emit(ScreenState.Error("Error"))
                    is Result.ApiSuccess -> _screenState.emit(SuccessData(it.data))
                }
            })

        }
    }

}


sealed class ScreenState() {
    object Loading : ScreenState()
    data class SuccessData(val data: Array<Products>) : ScreenState()
    data class Error(val error: String) : ScreenState()

}
