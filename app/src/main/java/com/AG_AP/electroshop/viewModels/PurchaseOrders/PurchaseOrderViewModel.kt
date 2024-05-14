package com.AG_AP.electroshop.viewModels.PurchaseOrders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.PurchaseOrderCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.uiState.Items.ArticleUiState
import com.AG_AP.electroshop.uiState.PurchaseOrders.PurchaseOrderUiState
import com.AG_AP.electroshop.viewModels.ActionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class PurchaseOrderViewModel : ViewModel(), ActionViewModel {

    private val _uiState = MutableStateFlow(PurchaseOrderUiState())
    val uiState: StateFlow<PurchaseOrderUiState> = _uiState.asStateFlow()

    init {
        DocumentLineForMutableList()
        BusinessPartnerCRUD.getAllObject { list ->
            val mutableList = list as? MutableList<BusinessPartner>
            mutableList?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        ListBusinessPartner = it.toList()
                    )
                }
            }
        }
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

    fun changeDocNum(docNum: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                DocNum = docNum
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

    fun changeSalesPersonCode(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                SalesPersonCode = name
            )
        }
    }

    fun changeValueInTable(lineNum: Int, position: Int, value: String) {
        //Sacamos el valor del numero de la linea
        val lineList = _uiState.value.DocumentLineList
        if (lineList != null) {
            val editedLine = lineList[lineNum]

            editedLine?.set(position, value)

            Log.i("Pruebas", "Linea: $lineNum, Posicion: $position, value: $value")

            val fullLine = _uiState.value.DocumentLineList
            Log.w("Pruebas", "Antes lista: ${fullLine.toString()}")

            if (editedLine != null) {
                fullLine.replace(lineNum, editedLine)


                Log.w("Pruebas", "Después lista: ${fullLine.toString()}")

                Log.e(
                    "Pruebas",
                    "Antes de actualizacion: ${_uiState.value.DocumentLineList.toString()}"
                )

                _uiState.update { currentState ->
                    currentState.copy(
                        DocumentLineList = fullLine
                    )
                }

                Log.e(
                    "Pruebas",
                    "lista actualizada? : ${_uiState.value.DocumentLineList.toString()}"
                )
            }
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
            currentState.copy(
                TaxDate = taxDate
            )
        }
    }

    fun showDialogBusinessPartner() {
        _uiState.update { currentState ->
            currentState.copy(
                showDialogBusinessPartner = true
            )
        }
    }

    fun closeDialogBusinessPartner() {
        _uiState.update { currentState ->
            currentState.copy(
                showDialogBusinessPartner = false
            )
        }
    }

    override fun save(data: Boolean) {
        val cardCode = _uiState.value.CardCode
        val cardName = _uiState.value.CardName
        val docNum = _uiState.value.DocNum
        val docDate = _uiState.value.DocDate
        val docDueDate = _uiState.value.DocDueDate
        val taxDate = _uiState.value.TaxDate
        val discountPercent = _uiState.value.DiscountPercent
        val SalesPersonCode = _uiState.value.SalesPersonCode.toInt()
        _uiState.update { currentState ->
            currentState.copy(
                DocumentLineList = trimDocumentLineList()
            )
        }
        val documentLine = hashMapToDocumentLine()
        var text = "Pedido de compra actualizado"

        val orderFireBase = OrderFireBase(
            "",
            docNum,
            cardCode,
            cardName,
            docDate,
            docDueDate,
            taxDate,
            discountPercent,
            documentLine,
            false,
            SalesPersonCode
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                PurchaseOrderCRUD.insertForFireBase(orderFireBase)
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error con la creación del pedido de compra"
            }

            if (!data) {
                val emptyList: MutableList<ArticleUiState?> = mutableListOf()
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
            } else {
                val lineToDelete = ArticleUiState(
                    0, "", "", 0.0F, 0.0F, 0.0F
                )

                val lineList = _uiState.value.DocumentLine
                val trimmedLine: MutableList<ArticleUiState?> = mutableListOf()
                lineList.forEach { line ->
                    if (line != null) {
                        if (!(line.ItemCode == lineToDelete.ItemCode && line.ItemDescription == lineToDelete.ItemDescription && line.Quantity == lineToDelete.Quantity && line.DiscountPercent == lineToDelete.DiscountPercent)) {
                            trimmedLine.add(line)
                        }
                    }
                }

                val documentLineList = DocumentLineForMutableList(trimmedLine)
                _uiState.update { currentState ->
                    currentState.copy(
                        DocumentLine = trimmedLine,
                        DocumentLineList = documentLineList,

                        message = true,
                        text = text
                    )
                }
            }
        }

    }

    override fun update() {
        val cardCode = _uiState.value.CardCode
        val cardName = _uiState.value.CardName
        val docNum = _uiState.value.DocNum
        val docDate = _uiState.value.DocDate
        val docDueDate = _uiState.value.DocDueDate
        val taxDate = _uiState.value.TaxDate
        val discountPercent = _uiState.value.DiscountPercent
        val SalesPersonCode = _uiState.value.SalesPersonCode.toInt()
        _uiState.update { currentState ->
            currentState.copy(
                DocumentLineList = trimDocumentLineList()
            )
        }
        val documentLine = hashMapToDocumentLine()
        var text = "Pedido de compra actualizado"

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
            false,
            SalesPersonCode
        )

        viewModelScope.launch {
            try {
                PurchaseOrderCRUD.updateObjectById(orderFireBase)
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error actualizando el pedido de compra"
            }

            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = text
                )
            }
            //TODO ACABAR mostrando las messages
        }
    }

    override fun delete() {
        val DocNum = _uiState.value.DocNum
        var text = "Pedido de compra eliminado"

        viewModelScope.launch {
            try {
                PurchaseOrderCRUD.deleteObjectById(DocNum.toString())
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error con el borrado del pedido de compra"
            }
            val emptyList: MutableList<ArticleUiState?> = mutableListOf()

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
            PurchaseOrderCRUD.getObjectById(_uiState.value.DocNum) { dataAux ->
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
                            text = "Pedido de compra no encontrador con número: ${_uiState.value.DocNum}"
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


    fun addLine() {
        val objectReflex = ArticleUiState(
            0, "", "", 0.0F, 0.0F, 0.0F
        )
        val size = _uiState.value.DocumentLine.size
        if (size != 0) {
            val endArticleList: ArticleUiState? = _uiState.value.DocumentLine.get(size - 1)
            if (objectReflex.equals(endArticleList)) {
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

    fun closeDialogaddArticle() {
        _uiState.update { currentState ->
            currentState.copy(
                showDialogAddArticle = false
            )
        }
    }

    fun showDialogaddArticle() {
        _uiState.update { currentState ->
            currentState.copy(
                showDialogAddArticle = true
            )
        }
    }

    fun addArticle(list: List<String>) {

        val objectReflex = ArticleUiState(
            0, "", "", 0.0F, 0.0F, 0.0F
        )

        //pruebas
        if (list[0].isEmpty() || list[1].isEmpty() || list[2].isEmpty() || list[3].isEmpty() || list[4].isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    showToast = true
                )
            }
            viewModelScope.launch {
                delay(3000)
                _uiState.update { currentState ->
                    currentState.copy(
                        showToast = false
                    )
                }
            }
            return
        } else {
            //fin pruebas
            if (_uiState.value.DocumentLine.size != 0) {
                val ultData = _uiState.value.DocumentLine[_uiState.value.DocumentLine.size - 1]
                if (objectReflex.equals(ultData)) {
                    _uiState.value.DocumentLine.removeAt(_uiState.value.DocumentLine.size - 1)
                }
            }

            var index = _uiState.value.DocumentLine.size
            index++
            _uiState.value.DocumentLine += ArticleUiState(
                index,
                list[0] ?: "",
                list[1] ?: "",
                list[2].toFloat() ?: 0.0F,
                list[3].toFloat() ?: 0.0F,
                list[4].toFloat() ?: 0.0F
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

}