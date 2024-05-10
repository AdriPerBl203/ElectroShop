package com.AG_AP.electroshop.viewModels.Items

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.uiState.Items.ItemUiState
import com.AG_AP.electroshop.viewModels.ActionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel(), ActionViewModel {

    private val _uiState = MutableStateFlow(ItemUiState())
    val uiState: StateFlow<ItemUiState> = _uiState.asStateFlow()

    init {
        val id: String = _uiState.value.ItemCode
        if (id.isNotEmpty()) {
            find()
        }
    }

    fun refresh() {
        val id: String = _uiState.value.ItemCode
        if (id.isNotEmpty()) {
            find()
        }
    }

    fun addItemPriceList(price: Price) {
        val list = _uiState.value.itemPrice
        list?.add(price)

        _uiState.update { currentState ->
            currentState.copy(
                itemPrice = list
            )
        }
    }

    fun changeItemCode(itemCode: String) {
        _uiState.update { currentState ->
            currentState.copy(
                ItemCode = itemCode
            )
        }
    }

    fun changeItemName(itemName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                itemName = itemName
            )
        }
    }

    fun changeItemType(itemType: ItemType) {
        _uiState.update { currentState ->
            currentState.copy(
                itemType = itemType
            )
        }
    }

    fun changeMainSupplier(mainSupplier: String) {
        _uiState.update { currentState ->
            currentState.copy(
                mainSupplier = mainSupplier
            )
        }
    }

    fun changeItemPrice(priceLists: MutableList<Price>?) {
        _uiState.update { currentState ->
            currentState.copy(
                itemPrice = priceLists
            )
        }
    }

    fun changeManageSerialNumbers(manageSerialNumbers: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                manageSerialNumbers = manageSerialNumbers
            )
        }
    }

    fun changeAutoCreateSerialNumbersOnRelease(autoCreateSerialNumbers: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                autoCreateSerialNumbersOnRelease = autoCreateSerialNumbers
            )
        }
    }

    fun changeShowBusinessPartnerDialog(boolean: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showBusinessPartnerDialog = boolean
            )
        }
    }

    fun changeShowListPricesDialog(boolean: Boolean) {
        _uiState.update { currentSate ->
            currentSate.copy(
                showPriceListDialog = boolean
            )
        }
    }

    fun eraseIndividualPriceList(price: Price) {
        val listAfterDel = _uiState.value.itemPrice
        var trash = _uiState.value.trash

        trash += 1
        listAfterDel?.remove(price)

        _uiState.update { currentState ->
            currentState.copy(
                itemPrice = listAfterDel,
                trash = trash
            )
        }

    }

    override fun guardar(data: Boolean) {
        val itemCode = _uiState.value.ItemCode
        val itemName = _uiState.value.itemName
        val itemType = _uiState.value.itemType
        val mainSupplier = if (_uiState.value.mainSupplier != "") {
            _uiState.value.mainSupplier
        } else {
            null
        }

        val itemPrice = _uiState.value.itemPrice
        val manageSerialNumbers = if (_uiState.value.manageSerialNumbers) {
            "tYES"
        } else {
            "tNO"
        }
        val autoCreateSerialNumbers = if (_uiState.value.autoCreateSerialNumbersOnRelease) {
            "tYES"
        } else {
            "tNO"
        }
        var text = "Nuevo articulo añadido"

        val item = Item(
            null,
            itemCode,
            itemName,
            itemType,
            mainSupplier,
            itemPrice,
            manageSerialNumbers,
            autoCreateSerialNumbers,
            false
        )

        viewModelScope.launch {
            try {
                ItemCRUD.insertItemForFireBase(item)
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error con la creación del artículo"
            }

            if (!data) {
                _uiState.update { currentState ->
                    currentState.copy(
                        message = true,
                        text = text,

                        ItemCode = "",
                        itemName = "",
                        itemType = ItemType.Articulo,
                        mainSupplier = "",
                        itemPrice = mutableListOf(),
                        manageSerialNumbers = false,
                        autoCreateSerialNumbersOnRelease = false,
                        showPriceListDialog = false,
                        showBusinessPartnerDialog = false,
                        trash = 0
                    )
                }
            }
        }

    }

    override fun update() {
        val itemCode = _uiState.value.ItemCode
        val itemName = _uiState.value.itemName
        val itemType = _uiState.value.itemType
        val mainSupplier = if (_uiState.value.mainSupplier != "") {
            _uiState.value.mainSupplier
        } else {
            null
        }
        val itemPrice = _uiState.value.itemPrice
        val manageSerialNumbers = if (_uiState.value.manageSerialNumbers) {
            "tYES"
        } else {
            "tNO"
        }
        val autoCreateSerialNumbers = if (_uiState.value.autoCreateSerialNumbersOnRelease) {
            "tYES"
        } else {
            "tNO"
        }
        var text = "Articulo actualizado"

        val item = Item(
            null,
            itemCode,
            itemName,
            itemType,
            mainSupplier,
            itemPrice,
            manageSerialNumbers,
            autoCreateSerialNumbers,
            false
        )

        viewModelScope.launch {
            try {
                ItemCRUD.updateItemById(item)
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error con la actualización del artículo"
            }

            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = text
                )
            }
        }


    }

    override fun borrar() {
        val itemCode = _uiState.value.ItemCode
        var text = "Articulo eliminado"

        viewModelScope.launch {
            try {
                ItemCRUD.deleteItemById(itemCode)
            } catch (e: Exception) {
                Log.e("Errores", e.stackTraceToString())
                text = "Hubo un error con el borrado del articulo"
            }

            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = text,

                    ItemCode = "",
                    itemName = "",
                    itemType = ItemType.Articulo,
                    mainSupplier = "",
                    itemPrice = mutableListOf(),
                    manageSerialNumbers = false,
                    autoCreateSerialNumbersOnRelease = false,
                    showPriceListDialog = false,
                    showBusinessPartnerDialog = false,
                    trash = 0
                )
            }


        }


    }

    override fun find() {
        if (_uiState.value.ItemCode.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = "Formato no válido"
                )
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            ItemCRUD.getItemById(_uiState.value.ItemCode) { dataAux ->
                if (dataAux != null) {
                    val manageSerialNumbers = dataAux.manageSerialNumbers == "tYes"
                    val autoCreateSerialNumbers = dataAux.autoCreateSerialNumbersOnRelease == "tYes"


                    _uiState.update { currentState ->
                        currentState.copy(
                            ItemCode = dataAux.ItemCode,
                            itemName = dataAux.itemName,
                            itemType = dataAux.itemType,
                            mainSupplier = dataAux.mainSupplier ?: "",
                            itemPrice = dataAux.itemPrice as MutableList<Price>?,
                            manageSerialNumbers = manageSerialNumbers,
                            autoCreateSerialNumbersOnRelease = autoCreateSerialNumbers,

                            showBusinessPartnerDialog = false,
                            showPriceListDialog = false,
                            trash = 0
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(
                            message = true,
                            text = "Item no encontrado con número: ${_uiState.value.ItemCode}"
                        )
                    }
                }
            }
        }
    }

    override fun menssageFunFalse() {
        _uiState.update { currentState ->
            currentState.copy(
                message = false
            )
        }
    }
}

