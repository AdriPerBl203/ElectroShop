package com.AG_AP.electroshop.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.uiState.ArticleUiState
import com.AG_AP.electroshop.uiState.OrderUiState
import com.AG_AP.electroshop.uiState.SettingUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel(),ActionViewModel {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    init {
        DocumentLineForMutableList()
    }

    fun refresh() {
        val docNum: Int = _uiState.value.DocNum
        if (docNum != -1) {
            find()
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
        if (_uiState.value.DocNum == -1) {
            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = "Formato no válido"
                )
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            OrderCRUD.getObjectById(_uiState.value.DocNum) { dataAux ->
                if (dataAux != null && dataAux is OrderFireBase) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            CardCode = dataAux.CardCode,
                            CardName = dataAux.CardName,
                            DocNum = dataAux.DocNum,
                            DocDate = dataAux.DocDate,
                            DocDueDate = dataAux.DocDueDate,
                            TaxDate = dataAux.TaxDate,
                            DiscountPercent = dataAux.DiscountPercent,
                            DocumentLine = getDataFromList(dataAux.DocumentLines), //TODO controlar la cantidad de lineas
                            DocumentLineList = DocumentLineForMutableListNoClear() //FIXME arreglar que no se ve
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(
                            message = true,
                            text = "Pedido de cliente no encontrador con número: ${_uiState.value.DocNum}"
                        )
                    }
                }
            }
        }
    }

    private fun getDataFromList(list: List<DocumentLineFireBase>): MutableList<ArticleUiState?> {
        val returnList: MutableList<ArticleUiState?> = mutableListOf()

        list.forEach { data ->
            val lineNum = data.LineNum
            val itemCode = data.ItemCode
            val itemDescription = data.ItemDescription
            val quantity = data.Quantity
            val price = data.Price
            val discount = data.DiscountPercent

            returnList.add(ArticleUiState(lineNum, itemCode, itemDescription, quantity.toFloat(), price.toFloat(), discount.toFloat()))
        }

        return returnList
    }

    override fun menssageFunFalse() {
        TODO("Not yet implemented")
    }

    private fun DocumentLineForMutableListNoClear(): MutableList<String> {
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

    fun changeDocNum(docNum: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                DocNum = docNum
            )
        }
    }
}