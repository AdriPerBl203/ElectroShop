package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.uiState.DialogArticleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DialogArticleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DialogArticleUiState())
    val uiState: StateFlow<DialogArticleUiState> = _uiState.asStateFlow()

    fun changeCodeArticle(it: String) {
        _uiState.update { currentState -> currentState.copy(
            codeArticle = it
        ) }
    }

    fun changeDescription(it: String) {
        _uiState.update { currentState -> currentState.copy(
            description = it
        ) }
    }

    fun changeCount(it: String) {
        _uiState.update { currentState -> currentState.copy(
            count = it
        ) }
    }

    fun changePrice(it: String) {
        _uiState.update { currentState -> currentState.copy(
            price = it
        ) }
    }

    fun changeDiscount(it: String) {
        _uiState.update { currentState -> currentState.copy(
            discount = it
        ) }
    }
}