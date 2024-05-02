package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.uiState.ArticleUiState
import com.AG_AP.electroshop.uiState.OrderUiState
import com.AG_AP.electroshop.uiState.SettingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderViewModel : ViewModel(),ActionViewModel {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    init {
        DocumentLineForMutableList()
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

    private fun DocumentLineForMutableList(): MutableList<String>{
        _uiState.value.DocumentLineList.clear()
        var index:Int =_uiState.value.DocumentLineList.size
        _uiState.value.DocumentLine.forEach{ element ->
            _uiState.value.DocumentLineList.add(index,element?.LineNum.toString())
            index++
            _uiState.value.DocumentLineList.add(index,element?.ItemCode.toString())
            index++
            _uiState.value.DocumentLineList.add(index,element?.ItemDescription.toString())
            index++
            _uiState.value.DocumentLineList.add(index,element?.Quantity.toString())
            index++
            _uiState.value.DocumentLineList.add(index,element?.Price.toString())
            index++
        }
        return _uiState.value.DocumentLineList
    }

    fun addLine(){
        val size = _uiState.value.DocumentLine.size
        var id:Int = _uiState.value.DocumentLine.lastOrNull()?.LineNum ?: 0
        var listAux: MutableList<ArticleUiState?> = _uiState.value.DocumentLine
        id++
        listAux.add(
            size
            , ArticleUiState(
                id,"","",0.0F,0.0F,0.0F
        )
        )
        var tastAux = _uiState.value.trash
        tastAux++
        _uiState.update { currentState -> currentState.copy(
            DocumentLine = listAux,
            DocumentLineList = DocumentLineForMutableList(),
            //TaxDate="asdasdfasdfasd"
            trash= tastAux
        ) }

    }

    fun deleteLine(){
        val size = _uiState.value.DocumentLine.size
        if(size-1 ==4){
            return
        }
        var listAux: MutableList<ArticleUiState?> = _uiState.value.DocumentLine
        listAux.removeAt(size -1)
        var tastAux = _uiState.value.trash
        tastAux++
        _uiState.update { currentState -> currentState.copy(
            DocumentLine = listAux,
            DocumentLineList = DocumentLineForMutableList(),
            trash= tastAux
        ) }
    }

    fun changeTaxDate(fechaDocumento: String) {
        //TODO
    }

    fun changeDiscount(it: String) {
        //TODO
    }

    fun changeName(it: String) {
        //TODO
    }

    fun changeDocDueDate(fechaDocumento: String) {
        //TODO
    }

    fun changeDocDate(fechaDocumento: String) {
        //TODO
    }
}