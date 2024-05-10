package com.AG_AP.electroshop.viewModels.Orders

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.uiState.Items.ArticleUiState
import com.AG_AP.electroshop.uiState.Orders.OrderUiState
import com.AG_AP.electroshop.viewModels.ActionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class OrderViewModel : ViewModel(), ActionViewModel {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    init {
        DocumentLineForMutableList()

        OrderCRUD.getAllObject { list ->
            val mutableList = list as? MutableList<OrderFireBase>
            mutableList?.let {
                _uiState.update { currentState -> currentState.copy(
                    ListItems = it.toList()
                ) }
            }
        }
    }

    fun refresh() {
        val docNum: Int = _uiState.value.DocNum
        if (docNum != -1) {
            find()
        }
    }

    override fun guardar(data: Boolean) {
        TODO("hacer cuando se agregen los insert" )
    }

    override fun update() {
        val cardCode = _uiState.value.CardCode
        val cardName = _uiState.value.CardName
        val docNum = _uiState.value.DocNum
        val docDate = _uiState.value.DocDate
        val docDueDate = _uiState.value.DocDueDate
        val taxDate = _uiState.value.TaxDate
        val discountPercent = _uiState.value.DiscountPercent
        _uiState.update { currentState ->
            currentState.copy(
                DocumentLineList = trimDocumentLineList()
            )
        }
        val documentLine = hashMapToDocumentLine()
        var text = "Pedido actualizado"

        val orderFireBase = OrderFireBase(
            null,
            docNum,
            cardCode,
            cardName,
            docDate,
            docDueDate,
            taxDate,
            discountPercent,
            documentLine,
            false
        )

        viewModelScope.launch {
            try {
                OrderCRUD.updateObjectById(orderFireBase)
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error actualizando el pedido"
            }

            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = text
                )
            }
            //TODO ACABAR mostrando las cosas
        }

    }

    override fun borrar() {
        val DocNum = _uiState.value.DocNum
        var text = "Pedido eliminado"

        viewModelScope.launch {
            try {
                OrderCRUD.deleteObjectById(DocNum.toString())
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error con el borrado del pedido"
            }
            val emptyList: MutableList<ArticleUiState?> = listOf(
                ArticleUiState(
                    0, "", "", 0.0F, 0.0F, 0.0F
                ),
                ArticleUiState(
                    1, "", "", 0.0F, 0.0F, 0.0F
                ),
                ArticleUiState(
                    2, "", "", 0.0F, 0.0F, 0.0F
                ),
                ArticleUiState(
                    3, "", "", 0.0F, 0.0F, 0.0F
                ),
                ArticleUiState(
                    4, "", "", 0.0F, 0.0F, 0.0F
                )
            ).toMutableList()


            val documentLineList = DocumentLineForMutableList(emptyList)

            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = text,
                    progress = false,

                    CardCode = "",
                    CardName = "",
                    DocNum = -1,
                    DocDate = "",
                    DocDueDate = "",
                    TaxDate = "",
                    DiscountPercent = 0.0,
                    DocumentLine = emptyList,
                    DocumentLineList = documentLineList,
                    trash = 0
                )
            }
        }

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
                    val dataList = getDataFromList(dataAux.DocumentLines)
                    val mutableDataList = DocumentLineForMutableList()

                    _uiState.update { currentState ->
                        currentState.copy(
                            CardCode = dataAux.CardCode,
                            CardName = dataAux.CardName,
                            DocNum = dataAux.DocNum,
                            DocDate = dataAux.DocDate,
                            DocDueDate = dataAux.DocDueDate,
                            TaxDate = dataAux.TaxDate,
                            DiscountPercent = dataAux.DiscountPercent,
                            DocumentLine = dataList,
                            DocumentLineList = mutableDataList
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

            returnList.add(
                ArticleUiState(
                    lineNum,
                    itemCode,
                    itemDescription,
                    quantity.toFloat(),
                    price.toFloat(),
                    discount.toFloat()
                )
            )
        }
        return returnList
    }

    override fun menssageFunFalse() {
        TODO("Not yet implemented")
    }

    private fun DocumentLineForMutableList(): ConcurrentHashMap<Int, MutableList<String>> {
        _uiState.value.DocumentLineList.clear()

        _uiState.value.DocumentLine.forEachIndexed { index, element ->
            if (element != null) {
                val listToAdd = element.let {
                    if (it.ItemDescription.toString() == "null") {
                        val ItemDescription = ""
                        mutableListOf(
                            it.LineNum.toString(),
                            it.ItemCode.toString(),
                            ItemDescription,
                            it.Quantity.toString(),
                            it.Price.toString(),
                            it.DiscountPercent.toString()
                        )
                    } else {
                        mutableListOf(
                            it.LineNum.toString(),
                            it.ItemCode.toString(),
                            it.ItemDescription.toString(),
                            it.Quantity.toString(),
                            it.Price.toString(),
                            it.DiscountPercent.toString()
                        )
                    }

                }
                _uiState.value.DocumentLineList[index] = listToAdd
            }
        }

        return _uiState.value.DocumentLineList
    }


    private fun trimDocumentLineList(): ConcurrentHashMap<Int, MutableList<String>> {
        val lineToDelete = ArticleUiState(
            0, "", "", 0.0F, 0.0F, 0.0F
        )

        val actualLineList = _uiState.value.DocumentLineList
        val listAfterDelete = _uiState.value.DocumentLineList

        Log.i("Pruebas", "Datos actuales: ${actualLineList.toString()}")
        actualLineList.forEach { (index, value) ->
            Log.i("Pruebas", "Datos dentro ${value.toString()}")

            var quantityToCompare = 5
            Log.i("Pruebas", "Datos de data: ${value.toString()}")

            if (lineToDelete.ItemCode == value[1].toString()) {
                quantityToCompare--
            }

            if (lineToDelete.ItemDescription == value[2].toString()) {
                quantityToCompare--
            }

            if (lineToDelete.Quantity.toString() == value[3].toString()) {
                quantityToCompare--
            }

            if (lineToDelete.Price.toString() == value[4].toString()) {
                quantityToCompare--
            }

            if (lineToDelete.DiscountPercent.toString() == value[5].toString()) {
                quantityToCompare--
            }


            if (quantityToCompare <= 0) {
                listAfterDelete.remove(index)
            }
        }

        return listAfterDelete
    }


    private fun hashMapToDocumentLine(): List<DocumentLineFireBase> {
        val listDocumentLineFireBase: MutableList<DocumentLineFireBase> = mutableListOf()

        val documentLineList = _uiState.value.DocumentLineList


        documentLineList.forEach { (index, value) ->
            val itemCode = value[1]
            val itemDescription = value[2]
            val quantity = value[3].toDouble()
            val price = value[4].toDouble()
            val discountPercent = value[5].toDouble()

            val newDocumentLine = DocumentLineFireBase(
                itemCode,
                itemDescription,
                quantity,
                discountPercent,
                index,
                price
            )
            listDocumentLineFireBase.add(newDocumentLine)
        }

        return listDocumentLineFireBase.toList()
    }

    private fun DocumentLineForMutableList(list: MutableList<ArticleUiState?>): ConcurrentHashMap<Int, MutableList<String>> {
        _uiState.value.DocumentLineList.clear()

        list.forEachIndexed { index, element ->
            if (element != null) {
                val listToAdd = element.let {
                    if (it.ItemDescription.toString() == "null") {
                        val ItemDescription = ""
                        mutableListOf(
                            it.LineNum.toString(),
                            it.ItemCode.toString(),
                            ItemDescription,
                            it.Quantity.toString(),
                            it.Price.toString(),
                            it.DiscountPercent.toString()
                        )
                    } else {
                        mutableListOf(
                            it.LineNum.toString(),
                            it.ItemCode.toString(),
                            it.ItemDescription.toString(),
                            it.Quantity.toString(),
                            it.Price.toString(),
                            it.DiscountPercent.toString()
                        )
                    }

                }
                _uiState.value.DocumentLineList[index] = listToAdd
            }
        }

        return _uiState.value.DocumentLineList
    }

    //FIXME arreglar
    fun addLine() {
        val objectReflex = ArticleUiState(
            0, "", "", 0.0F, 0.0F, 0.0F
        )
        val size = _uiState.value.DocumentLine.size
        if(size !=0){
            val endArticleList : ArticleUiState? = _uiState.value.DocumentLine.get(size -1)
            if(objectReflex.equals(endArticleList)){
                return
            }
        }

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
                DocumentLineList = DocumentLineForMutableList(),
                //TaxDate="asdasdfasdfasd"
                trash = tastAux
            )
        }

    }

    fun deleteLine() {
        val size = _uiState.value.DocumentLine.size
        if (size == 0) {
            return
        }
        var listAux: MutableList<ArticleUiState?> = _uiState.value.DocumentLine
        listAux.removeAt(size - 1)
        var tastAux = _uiState.value.trash
        tastAux++
        _uiState.update { currentState ->
            currentState.copy(
                DocumentLine = listAux,
                DocumentLineList = DocumentLineForMutableList(),
                trash = tastAux
            )
        }
    }

    fun changeTaxDate(fechaDocumento: String) {
        _uiState.update { currentState ->
            currentState.copy(
                TaxDate = fechaDocumento
            )
        }
    }

    fun changeDiscount(it: String) {
        //TODO
    }

    fun changeName(it: String) {
        //TODO
    }

    fun changeDocDueDate(fechaDocumento: String) {
        _uiState.update { currentState ->
            currentState.copy(
                DocDueDate = fechaDocumento
            )
        }
    }

    fun changeDocDate(fechaDocumento: String) {
        _uiState.update { currentState ->
            currentState.copy(
                DocDate = fechaDocumento
            )
        }
    }

    fun changeDocNum(docNum: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                DocNum = docNum
            )
        }
    }

    fun showDialogaddArticle(){
        _uiState.update { currentState ->
            currentState.copy(
                showDialogAddArticle=true
            )
        }
    }

    fun closeDialogaddArticle(){
        _uiState.update { currentState ->
            currentState.copy(
                showDialogAddArticle=false
            )
        }
    }
    fun addArticle(list:List<String>){

        //Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT).show()
        //pruebas
        if(list[0].isNullOrEmpty() || list[1].isNullOrEmpty() || list[2].isNullOrEmpty() || list[3].isNullOrEmpty() || list[4].isNullOrEmpty()){
            //TODO
        }
        //fin pruebas
        if(_uiState.value.DocumentLine.size !=0 ){
            _uiState.value.DocumentLine.removeAt(_uiState.value.DocumentLine.size -1)
        }

        var index = _uiState.value.DocumentLine.size
        index++
        _uiState.value.DocumentLine+= ArticleUiState(
            index,
            list[0] ?: "",
            list[1] ?: "",
            list[2].toFloat() ?:0.0F,
            list[3].toFloat() ?:0.0F,
            list[4].toFloat() ?:0.0F
        )
        var tastAux = _uiState.value.trash
        tastAux++
        _uiState.update { currentState ->
            currentState.copy(
                DocumentLineList = DocumentLineForMutableList(),
                trash = tastAux
            )
        }

    }
}