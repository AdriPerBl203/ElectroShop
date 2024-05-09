package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.PriceListCRUD
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.uiState.DialogPLUiState
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



        if (priceList != null && choosenCurrency != null && writtenPrice != null) {
            val itemPrice = Price(priceList.toInt(), writtenPrice, choosenCurrency, true)

            _uiState.update { currentState ->
                currentState.copy(
                    ItemPrice = itemPrice
                )
            }
        }
    }


}