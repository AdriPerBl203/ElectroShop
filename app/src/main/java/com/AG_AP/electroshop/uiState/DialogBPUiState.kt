package com.AG_AP.electroshop.uiState

data class DialogBPUiState (
    val BusinessPartnerList: List<BusinessPartnerUiState> = listOf(),
    val ChosenBusinessPartner: BusinessPartnerUiState? = null,
    val IsSomeoneSelected: Boolean = false
)