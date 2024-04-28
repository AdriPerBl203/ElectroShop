package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.uiState.ListActivityUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListActivityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ListActivityUiState())
    val uiState: StateFlow<ListActivityUiState> = _uiState.asStateFlow()

    init {
        ActivityCRUD.getAllActivity() {lista ->
            _uiState.update { currentState -> currentState.copy(
                ListActivity = lista
            ) }
        }
    }

    fun changeDateInit(it: String) {
        _uiState.update { currentState -> currentState.copy(
            DateInit = it
        ) }
    }
    fun changeDateEnd(it: String) {
        _uiState.update { currentState -> currentState.copy(
            DateEnd = it
        ) }
    }
    fun changeClient(it: String) {
        _uiState.update { currentState -> currentState.copy(
            Client = it
        ) }
    }
    fun changeDocument(it: String) {
        _uiState.update { currentState -> currentState.copy(
            Document = it
        ) }
    }

     fun menssageFunFalse() {
        _uiState.update { currentState -> currentState.copy(
            message = false
        ) }
    }
}