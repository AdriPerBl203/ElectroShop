package com.AG_AP.electroshop.uiState

import com.AG_AP.electroshop.firebase.models.BusinessPartner

data class ListBusinessPartnerUiState(
    val ListBusinessPartner: List<BusinessPartner?> = listOf(),
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = ""
)
