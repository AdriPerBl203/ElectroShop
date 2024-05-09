package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.uiState.ItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ItemViewModel : ViewModel(), ActionViewModel {

    private val _uiState = MutableStateFlow(ItemUiState())
    val uiState: StateFlow<ItemUiState> = _uiState.asStateFlow()

    fun refresh() {
        TODO()
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

        listAfterDel?.remove(price)

        _uiState.update { currentState ->
            currentState.copy(
                itemPrice = listAfterDel
            )
        }
        //FIXME  arreglar deque se actualize en tiempo reals mainito

    }

    override fun guardar(data: Boolean) {
        TODO("Not yet implemented, controlar los booleanos")
    }

    override fun update() {
        TODO("Not yet implemented, controlar los booleanos")
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

