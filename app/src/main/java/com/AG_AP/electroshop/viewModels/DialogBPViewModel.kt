package com.AG_AP.electroshop.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.uiState.BusinessPartnerUiState
import com.AG_AP.electroshop.uiState.DialogBPUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DialogBPViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DialogBPUiState())
    val uiState: StateFlow<DialogBPUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateBusinessPartnersWthoClient()
        }
    }


    fun changeSelection(isSelected: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                IsSomeoneSelected = isSelected
            )
        }
    }


    fun changeBusinessPartnerList(list: List<BusinessPartnerUiState>) {
        if (list.isNotEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    BusinessPartnerList = list
                )
            }
        }
    }

    fun updateBusinessPartnersWthoClient() {
        var businessPartnerAuxList: MutableList<BusinessPartnerUiState> = mutableListOf()
        var lineNum = 0

        BusinessPartnerCRUD.getAllObject { businessPartners ->
            if (businessPartners != null) {
                businessPartners as MutableList<BusinessPartner>

                businessPartners.forEach { dato ->
                    if (dato.CardType.trim() != "cCustomer") {
                        val cardCode = dato.CardCode
                        val cardType = dato.CardType
                        val cardName = dato.CardName
                        val cellular = dato.Cellular
                        val emailAdress = dato.EmailAddress

                        val businessPartner = BusinessPartnerUiState(
                            lineNum,
                            cardCode,
                            cardType,
                            cardName,
                            cellular,
                            emailAdress
                        )

                        businessPartnerAuxList.add(businessPartner)
                        lineNum++
                        Log.i("Pruebas", businessPartner.toString())
                    }
                }
                changeBusinessPartnerList(businessPartnerAuxList)
            }
        }
    }


}