package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.InvoiceDataCRUD
import com.AG_AP.electroshop.firebase.models.InvoiceData
import com.AG_AP.electroshop.uiState.InvoiceUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InvoiceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InvoiceUiState())
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()

    init {
        searchData()
    }


    /**
     * Method that searches for new invoice in the database and updates the list inside the uiState
     */
    private fun searchData() {
        var mutableList: MutableList<InvoiceData?> = mutableListOf()
        InvoiceDataCRUD.getAllObject {
            mutableList = it

        }

        _uiState.update { currentState ->
            currentState.copy(
                BusinessPartnerWithInvoiceList = mutableList,
                BusinessPartnerWithInvoiceListBackud = mutableList
            )
        }

    }

    fun cardNameChange(it: String) {

        if(_uiState.value.CardName.length>2){
            _uiState.value.BusinessPartnerWithInvoiceList =mutableListOf()
            _uiState.value.BusinessPartnerWithInvoiceListBackud.forEach{ x ->
                if (x != null) {
                    if(x.CardCode.contains(it)){
                        _uiState.value.BusinessPartnerWithInvoiceList+=x
                    }
                }
            }
        }else{
            _uiState.value.BusinessPartnerWithInvoiceList = _uiState.value.BusinessPartnerWithInvoiceListBackud
        }
        _uiState.update { currentState ->
            currentState.copy(
                CardName = it
            )
        }
    }

    fun replaceData(item: InvoiceData?) {
        if (item != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    //TODO
                )
            }
        }
    }

}