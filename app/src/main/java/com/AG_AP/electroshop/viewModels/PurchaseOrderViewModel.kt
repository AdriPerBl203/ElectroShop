package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.uiState.PurchaseOrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PurchaseOrderViewModel : ViewModel(),ActionViewModel, DatePickerViewModel {

    private val _uiState = MutableStateFlow(PurchaseOrderUiState())
    val uiState: StateFlow<PurchaseOrderUiState> = _uiState.asStateFlow()
    override val selectedDate: String
        get() {
            return uiState.value.DocDate
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

    override fun onDateSelected(date: String) {
        TODO("Not yet implemented")
    }
}