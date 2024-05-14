package com.AG_AP.electroshop.uiState.BusinessPartners

import com.AG_AP.electroshop.firebase.models.BusinessPartner

data class BusinessPartnerUiState(
    val LineNum: Int = 0,
    val CardCode:String ="",
    val CardType:String ="",
    val CardName:String ="",
    val Cellular:String ="",
    val EmailAddress:String ="",
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = "",
    val Option: String = "",

    val BPSapList: List<BusinessPartner?> = listOf(),
    val BPDeviceList: List<BusinessPartner?> = listOf(),
    val FilterByName: String = ""
)
