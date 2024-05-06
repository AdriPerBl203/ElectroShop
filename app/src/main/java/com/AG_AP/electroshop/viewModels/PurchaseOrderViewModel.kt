package com.AG_AP.electroshop.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.uiState.ArticleUiState
import com.AG_AP.electroshop.uiState.PurchaseOrderUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PurchaseOrderViewModel : ViewModel(), ActionViewModel {

    private val _uiState = MutableStateFlow(PurchaseOrderUiState())
    val uiState: StateFlow<PurchaseOrderUiState> = _uiState.asStateFlow()

    init {
        documentLineForMutableList()
    }

    fun refresh() {
        val docNum: Int = _uiState.value.DocNum
        if (docNum != -1) {
            find()
        }
    }

    fun changeCardCode(cardCode: String) {
        _uiState.update { currentState ->
            currentState.copy(
                CardCode = cardCode
            )
        }
    }

    fun changeName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                CardName = name
            )
        }
    }

    fun changeDiscount(discount: String) {
        try {
            val disc = discount.toFloat()

            if (disc >= 100.0) {
                _uiState.update { currentState ->
                    currentState.copy(
                        DiscountPercent = 100.0
                    )
                }
            } else if (disc <= 0.0) {
                _uiState.update { currentState ->
                    currentState.copy(
                        DiscountPercent = 0.0
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        DiscountPercent = discount.toDouble()
                    )
                }
            }
            return
        } catch (e: Exception) {
            Log.e("Errores", e.stackTraceToString())
        }
    }

    fun changeDocDate(docDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                DocDate = docDate
            )
        }
    }

    fun changeDocDueDate(docDueDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                DocDueDate = docDueDate
            )
        }
    }

    fun changeTaxDate(taxDate: String) {
        _uiState.update { currentState ->
            currentState
        }
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
        TODO()
    }

    override fun menssageFunFalse() {
        TODO("Not yet implemented")
    }


    fun addLine() {
        val size = _uiState.value.DocumentLine.size
        var id: Int = _uiState.value.DocumentLine.lastOrNull()?.LineNum ?: 0
        var listAux: MutableList<ArticleUiState?> = _uiState.value.DocumentLine
        id++
        listAux.add(
            size, ArticleUiState(
                id, "", "", 0.0F, 0.0F, 0.0F
            )
        )
        var tastAux = _uiState.value.trash
        tastAux++
        _uiState.update { currentState ->
            currentState.copy(
                DocumentLine = listAux,
                DocumentLineList = documentLineForMutableList(),
                //TaxDate="asdasdfasdfasd"
                trash = tastAux
            )
        }

    }

    private fun documentLineForMutableList(): MutableList<String> {
        _uiState.value.DocumentLineList.clear()
        var index: Int = _uiState.value.DocumentLineList.size
        _uiState.value.DocumentLine.forEach { element ->
            _uiState.value.DocumentLineList.add(index, element?.LineNum.toString())
            index++
            _uiState.value.DocumentLineList.add(index, element?.ItemCode.toString())
            index++
            _uiState.value.DocumentLineList.add(index, element?.ItemDescription.toString())
            index++
            _uiState.value.DocumentLineList.add(index, element?.Quantity.toString())
            index++
            _uiState.value.DocumentLineList.add(index, element?.Price.toString())
            index++
        }
        return _uiState.value.DocumentLineList
    }

    fun deleteLine() {
        val size = _uiState.value.DocumentLine.size
        if (size - 1 == 4) {
            return
        }
        var listAux: MutableList<ArticleUiState?> = _uiState.value.DocumentLine
        listAux.removeAt(size - 1)
        var tastAux = _uiState.value.trash
        tastAux++
        _uiState.update { currentState ->
            currentState.copy(
                DocumentLine = listAux,
                DocumentLineList = documentLineForMutableList(),
                trash = tastAux
            )
        }
    }
}