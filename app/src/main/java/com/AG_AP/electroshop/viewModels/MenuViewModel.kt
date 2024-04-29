package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.functions.SessionObj
import com.AG_AP.electroshop.uiState.MenuUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MenuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState -> currentState.copy(
            username = SessionObj.name,
            articulo= SessionObj.articulo,
            actividad = SessionObj.actividad,
            pedidoCL= SessionObj.pedidoCL,
            pedidoCO= SessionObj.pedidoCO,
        ) }
    }
}