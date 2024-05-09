package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.uiState.DialogArticleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DialogArticleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DialogArticleUiState())
    val uiState: StateFlow<DialogArticleUiState> = _uiState.asStateFlow()

    init {
        OrderCRUD.getAllObject { list ->
            val mutableList = list as? MutableList<OrderFireBase>
            mutableList?.let {
                _uiState.update { currentState -> currentState.copy(
                    ListItems = it.toList()
                ) }
            }
        }
    }

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

    fun closeDialogSelectCodeArticle() {
        _uiState.update { currentState -> currentState.copy(
            showDialogSelectCodeArticle = false,
        ) }
    }

    fun showDialogSelectCode() {
        _uiState.update { currentState -> currentState.copy(
            showDialogSelectCodeArticle = true,
            discount ="",
            price ="",
            count ="",
            description ="",
            codeArticle ="",
        ) }
    }
}