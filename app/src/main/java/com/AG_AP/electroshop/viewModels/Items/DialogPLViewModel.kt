package com.AG_AP.electroshop.viewModels.Items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.PriceListCRUD
import com.AG_AP.electroshop.firebase.models.ItemPrice
import com.AG_AP.electroshop.uiState.Items.DialogPLUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DialogPLViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DialogPLUiState())
    val uiState: StateFlow<DialogPLUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateCurrencyList()

            _uiState.update { currentState ->
                currentState.copy(
                    ItemPrice = null
                )
            }
        }
    }


    fun updateCurrencyList() {
        PriceListCRUD.getAllPrecios { prices ->
            prices.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        AvailablePriceList = prices
                    )
                }
            }
        }
    }

    fun changePriceList(priceList: Number) {
        _uiState.update { currentState ->
            currentState.copy(
                PriceList = priceList
            )
        }
    }

    fun changeChoosenCurrency(currency: String) {
        _uiState.update { currentState ->
            currentState.copy(
                ChoosenCurrency = currency
            )
        }
    }

    fun changeWrittenPrice(price: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                PriceWritten = price
            )
        }
    }

    fun changePrice() {
        val priceList = _uiState.value.PriceList
        val choosenCurrency = _uiState.value.ChoosenCurrency
        val writtenPrice = _uiState.value.PriceWritten

        if (priceList != null && (choosenCurrency != null && choosenCurrency != "") && (writtenPrice != null && writtenPrice >= 0.0)) {
            //priceList.toInt(), writtenPrice, choosenCurrency, true
            val itemPrice = ItemPrice().apply {
                this.priceList = priceList.toInt()
                this.currency = choosenCurrency
                this.price = writtenPrice
                this.SAP = true
            }

            _uiState.update { currentState ->
                currentState.copy(
                    ItemPrice = itemPrice
                )
            }
        }
    }

    fun clearData() {
        _uiState.update { currentState ->
            currentState.copy(
                PriceList = 0,
                ChoosenCurrency = "",
                PriceWritten = 0.0,
                ItemPrice = null
            )
        }
    }


}