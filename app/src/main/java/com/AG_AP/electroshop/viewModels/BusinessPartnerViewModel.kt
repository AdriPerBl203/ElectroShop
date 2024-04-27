package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.uiState.BusinessPartnerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BusinessPartnerViewModel : ViewModel(),ActionViewModel {

    private val _uiState = MutableStateFlow(BusinessPartnerUiState())
    val uiState: StateFlow<BusinessPartnerUiState> = _uiState.asStateFlow()

    fun changeCardCode(it: String) {
        _uiState.update { currentState -> currentState.copy(
            CardCode = it
        ) }
    }
    fun changeCardType(it: String) {
        _uiState.update { currentState -> currentState.copy(
            CardType = it
        ) }
    }
    fun changeCardName(it: String) {
        _uiState.update { currentState -> currentState.copy(
            CardName = it
        ) }
    }
    fun changeCellular(it: String) {
        _uiState.update { currentState -> currentState.copy(
            Cellular = it
        ) }
    }
    fun changeEmailAddress(it: String) {
        _uiState.update { currentState -> currentState.copy(
            EmailAddress = it
        ) }
    }

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