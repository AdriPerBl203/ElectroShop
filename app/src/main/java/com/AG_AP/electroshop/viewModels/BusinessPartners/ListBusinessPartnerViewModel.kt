package com.AG_AP.electroshop.viewModels.BusinessPartners

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.uiState.BusinessPartners.ListBusinessPartnerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListBusinessPartnerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ListBusinessPartnerUiState())
    val uiState: StateFlow<ListBusinessPartnerUiState> = _uiState.asStateFlow()


    init {
        BusinessPartnerCRUD.getAllObject { lista ->
            _uiState.update { currentState ->
                currentState.copy(
                    ListBusinessPartner = lista as List<BusinessPartner?>
                )
            }
        }
    }


}