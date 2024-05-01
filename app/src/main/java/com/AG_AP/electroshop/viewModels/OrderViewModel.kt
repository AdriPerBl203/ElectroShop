package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.uiState.OrderUiState
import com.AG_AP.electroshop.uiState.SettingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderViewModel : ViewModel(),ActionViewModel {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    override fun guardar(data: Boolean) {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun borrar() {
        TODO("Not yet implemented")
    }

    override fun find() {
        TODO("Not yet implemented")
    }

    override fun menssageFunFalse() {
        TODO("Not yet implemented")
    }
}