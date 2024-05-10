package com.AG_AP.electroshop.uiState.BusinessPartners

import com.AG_AP.electroshop.uiState.BusinessPartners.BusinessPartnerUiState

data class DialogBPUiState (
    val BusinessPartnerList: List<BusinessPartnerUiState> = listOf(),
    val ChosenBusinessPartner: BusinessPartnerUiState? = null,
    val IsSomeoneSelected: Boolean = false
)