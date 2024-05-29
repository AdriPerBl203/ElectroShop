package com.AG_AP.electroshop.viewModels.Orders

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.SEIConfigCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.firebase.models.SEIConfig
import com.AG_AP.electroshop.functions.InterconexionUpdateArticle
import com.AG_AP.electroshop.functions.ObjectContext
import com.AG_AP.electroshop.uiState.Items.ArticleUiState
import com.AG_AP.electroshop.uiState.Orders.OrderUiState
import com.AG_AP.electroshop.viewModels.ActionViewModel
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

        val sharedPref =
            ObjectContext.context.getSharedPreferences("userConected", Context.MODE_PRIVATE)
        val savedUserName = sharedPref.getString("userConected", null)

        if (savedUserName != null) {
            SEIConfigCRUD.getSEIConfigById(savedUserName) { it ->
                if (it is SEIConfig) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            SalesPersonCode = it.U_Empleado.toString()
                        )
                    }
                }
            }
        }


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

    fun updateLists() {
        OrderCRUD.getAllObject { list ->
            val mutableList = list as? MutableList<OrderFireBase>
            mutableList?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        ListItems = it.toList()
                    )
                }
            }
        }

        OrderCRUD.getOrdersInSap(true) { list ->
            val mutableList = list as? MutableList<OrderFireBase>
            mutableList?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        ListOrdersInSap = it.toList()
                    )
                }
            }
        }

        OrderCRUD.getOrdersInSap(false) { list ->
            val mutableList = list as? MutableList<OrderFireBase>
            mutableList?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        ListOrdersInDevice = it.toList()
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

    fun changeActualDate() {
        _uiState.update { currentState ->
            currentState.copy(
                actualDate = false
            )
        }
    }

    fun changeDates(date: String) {
        _uiState.update { currentState ->
            currentState.copy(
                DocDate = date,
                DocDueDate = date,
                TaxDate = date
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
        var text = "Pedido de venta actualizado"


        var orderFireBase: OrderFireBase = OrderFireBase().apply {
            this.CardCode = cardCode
            this.CardName = cardName
            this.DocDate = docDate
            this.DocDueDate = docDueDate
            this.DocNum = docNum
            this.DiscountPercent = discountPercent
            this.TaxDate = taxDate
            this.SalesPersonCode = SalesPersonCode
            this.DocumentLines = documentLine
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                OrderCRUD.insert(orderFireBase)
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error con la creación del pedido de venta"
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
                        trash = 0,
                        totalPrice = 0.0
                    )
                }
            } else {
                val lineToDelete = ArticleUiState(
                    0, "", "", 0.0, 0.0, 0.0
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
        val SalesPersonCode = _uiState.value.SalesPersonCode.toInt()
        val discountPercent = _uiState.value.DiscountPercent
        _uiState.update { currentState ->
            currentState.copy(
                DocumentLineList = trimDocumentLineList()
            )
        }
        val documentLine = hashMapToDocumentLine()
        var text = "Pedido actualizado"

        val orderFireBase = OrderFireBase().apply {
            this.DocNum = docNum
            this.CardCode = cardCode
            this.CardName = cardName
            this.DocDate = docDate
            this.DocDueDate = docDueDate
            this.TaxDate = taxDate
            this.DiscountPercent = discountPercent
            this.DocumentLines = documentLine
            this.SalesPersonCode = SalesPersonCode
        }

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

    override fun delete() {
        val DocNum = _uiState.value.DocNum
        var text = "Pedido eliminado"

        viewModelScope.launch {
            try {
                OrderCRUD.deleteObjectById(DocNum.toString())
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error con el borrado del pedido"
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
                    trash = 0,
                    totalPrice = 0.0
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
                            CardName = dataAux.CardName ?: "",
                            DocNum = dataAux.DocNum,
                            DocDate = dataAux.DocDate,
                            DocDueDate = dataAux.DocDueDate,
                            TaxDate = dataAux.TaxDate,
                            DiscountPercent = dataAux.DiscountPercent,
                            DocumentLine = dataList,
                            DocumentLineList = mutableDataList,
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
                    quantity.toDouble(),
                    price.toDouble(),
                    discount.toDouble()
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
            0, "", "", 0.0, 0.0, 0.0
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


    private fun hashMapToDocumentLine(): RealmList<DocumentLineFireBase> {
        val listDocumentLineFireBase: MutableList<DocumentLineFireBase> = mutableListOf()

        val documentLineList = _uiState.value.DocumentLineList


        documentLineList.forEach { (index, value) ->
            val itemCode = value[1]
            val itemDescription = value[2]
            val quantity = value[3].toDouble()
            val price = value[4].toDouble()
            val discountPercent = value[5].toDouble()

            val newDocumentLine = DocumentLineFireBase().apply {
                this.LineNum = index
                this.ItemCode = itemCode
                this.ItemDescription = itemDescription
                this.Quantity = quantity
                this.Price = price
                this.DiscountPercent = discountPercent
            }
            listDocumentLineFireBase.add(newDocumentLine)
        }

        return listDocumentLineFireBase.toRealmList()
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

    fun addLine() {
        val objectReflex = ArticleUiState(
            0, "", "", 0.0, 0.0, 0.0
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
                id, "", "", 0.0, 0.0, 0.0
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

    fun changeSalesPersonCode(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                SalesPersonCode = name
            )
        }
    }

    fun changeTotalPrice() {
        val lista = _uiState.value.DocumentLineList
        val size = _uiState.value.DocumentLine.size
        var sumaTotal = 0.0

        if (size > 0) {
            lista.forEach { (index, value) ->
                try {
                    sumaTotal += value[4].toDouble()
                } catch (e: Exception) {
                    Log.e("Errores", e.stackTraceToString())
                }
            }
        }

        _uiState.update { currentState ->
            currentState.copy(
                totalPrice = sumaTotal
            )
        }

    }

    fun changeDiscount(it: String) {
        var disc = 0.0
        try {
            var decimal = it.substringAfter(".", "")
            var num = it.substringBefore(".", "")

            if (decimal.length > 2) {
                decimal = decimal.substring(2)
            }

            if (num.length > 3) {
                num = num.substring(3)
            }


            val numTotal = "$num.$decimal"
            disc = numTotal.toDouble()

            if (disc <= 0.0) {
                disc = 0.0
            } else if (disc >= 100.0) {
                disc = 100.0
            }
        } catch (e: Exception) {
            Log.e("Errores", e.stackTraceToString())
        }

        _uiState.update { currentState ->
            currentState.copy(
                DiscountPercent = disc
            )
        }
    }

    fun changeName(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                CardName = it
            )
        }
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

    fun changeCardCode(data: String) {
        _uiState.update { currentState ->
            currentState.copy(
                CardCode = data
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

    fun closeDialogaddArticle() {
        _uiState.update { currentState ->
            currentState.copy(
                showDialogAddArticle = false
            )
        }
    }


    fun addArticle(list: List<String>) {

        val objectReflex = ArticleUiState(
            0, "", "", 0.0, 0.0, 0.0
        )

        //pruebas
        if (list[0].isEmpty() || list[1].isEmpty() || list[2].isEmpty() || list[3].isEmpty() || list[4].isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    text = "Existen algunos campos vacios",
                    showToast = true
                )
            }
            viewModelScope.launch {
                delay(3000)
                _uiState.update { currentState ->
                    currentState.copy(
                        text = "",
                        showToast = false
                    )
                }
            }
            return
        } else {

            when (InterconexionUpdateArticle.index) {
                -1 -> {

                    if (_uiState.value.DocumentLine.size != 0) {
                        val ultData =
                            _uiState.value.DocumentLine[_uiState.value.DocumentLine.size - 1]
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
                        list[2].toDouble() ?: 0.0,
                        list[3].toDouble() ?: 0.0,
                        list[4].toDouble() ?: 0.0
                    )

                }

                else -> {

                    val i = InterconexionUpdateArticle.index
                    _uiState.value.DocumentLine[i] = ArticleUiState(
                        i,
                        list[0] ?: "",
                        list[1] ?: "",
                        list[2].toDouble() ?: 0.0,
                        list[3].toDouble() ?: 0.0,
                        list[4].toDouble() ?: 0.0
                    )
                    InterconexionUpdateArticle.index = -1
                }
            }

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

    fun ejecutarAction(navController: NavHostController) {
        if (check()) {
            when (_uiState.value.ActionButton) {
                "Añadir y ver" -> save(true)
                "Añadir y nuevo" -> save(false)
                "Añadir y salir" -> {
                    save(false)
                    navController.popBackStack()
                }

                "Actualizar" -> update()
                "Borrar" -> delete()
                else -> ""
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    text = "Falta rellenar algún campo o añadir al menos un articulo",
                    showToast = true
                )
            }
            viewModelScope.launch {
                delay(3000)
                _uiState.update { currentState ->
                    currentState.copy(
                        text = "",
                        showToast = false
                    )
                }
            }
        }
    }

    fun changeActionButton(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                ActionButton = it
            )
        }
    }

    fun check(): Boolean {
        val cardCode = _uiState.value.CardCode
        val mainSupplier = _uiState.value.SalesPersonCode
        val fechaContabilizacion = _uiState.value.TaxDate
        val fechaEntrega = _uiState.value.DocDueDate
        val fechaDocumento = _uiState.value.DocDate
        val lista = _uiState.value.DocumentLineList

        var allCheck = false

        if (cardCode.isNotEmpty() && mainSupplier.isNotEmpty() && fechaContabilizacion.isNotEmpty() && fechaEntrega.isNotEmpty() && fechaDocumento.isNotEmpty() && lista.isNotEmpty()) {
            allCheck = true
        }

        return allCheck
    }

    fun showOrderComplete(order: OrderFireBase) {
        val documentLine: MutableList<ArticleUiState?> = mutableListOf()
        var numInicial = 0

        order.DocumentLines.forEach {
            Log.i("Pruebas", numInicial.toString())
            val item = ArticleUiState(
                LineNum = numInicial,
                ItemCode = it.ItemCode,
                ItemDescription = it.ItemDescription,
                Quantity = it.Quantity,
                Price = it.Price,
                DiscountPercent = it.DiscountPercent,
            )

            documentLine.add(item)
            numInicial = numInicial.plus(1)

        }

        _uiState.value.DocumentLine.clear()
        _uiState.value.DocumentLineList.clear()

        _uiState.update { currentState ->
            currentState.copy(
                CardCode = order.CardCode,
                CardName = order.CardName ?: "",
                DocNum = order.DocNum,
                DocDate = order.DocDate,
                DocDueDate = order.DocDueDate,
                TaxDate = order.TaxDate,
                DiscountPercent = order.DiscountPercent,
                SalesPersonCode = order.Slpcode,
                DocumentLine = documentLine,
                DocumentLineList = DocumentLineForMutableListSinceListOrder(order.DocumentLines.toMutableList()),
                actualDate = true
            )
        }

    }

    private fun DocumentLineForMutableListSinceListOrder(listItems: MutableList<DocumentLineFireBase>): ConcurrentHashMap<Int, MutableList<String>> {
        listItems.forEachIndexed { index, element ->
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

    fun deleteObject(value: MutableList<String>) {
        //TODO
        val obejctDelete = ArticleUiState(
            value[0].toInt(),
            value[1],
            value[2],
            value[3].toDouble(),
            value[4].toDouble(),
            value[5].toDouble()
        )
        Log.i("Pruebas", value.toString())

        _uiState.value?.DocumentLine?.let { documentLines ->

            var index: Int = -1
            documentLines.forEachIndexed { i, x ->
                if (x != null) {
                    if (x.equals(obejctDelete)) {
                        index = i
                    }
                }
            }
            if (index != -1) {
                var tastAux = _uiState.value.trash
                tastAux++
                _uiState.value.DocumentLine.removeAt(index)
                _uiState.update { currentState ->
                    currentState.copy(
                        DocumentLineList = DocumentLineForMutableList(),
                        trash = tastAux
                    )
                }
            }
        }
    }


    fun editarArticulo(index: Int) {
        Log.i("EditarArticulo", index.toString())
    }
}