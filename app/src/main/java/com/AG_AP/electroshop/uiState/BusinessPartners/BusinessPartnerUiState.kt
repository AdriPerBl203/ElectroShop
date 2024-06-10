package com.AG_AP.electroshop.uiState.BusinessPartners

import com.AG_AP.electroshop.realm.models.BusinessPartner

data class BusinessPartnerUiState(
    val LineNum: Int = 0,
    val CardCode: String = "", //quitar C00001
    val CardType: String = "Cliente",
    val CardName: String = "",
    val Cellular: String = "",
    val EmailAddress: String = "",
    val message: Boolean = false,
    val progress: Boolean = false,
    val text: String = "",
    val Option: String = "Añadir y ver",
    val showDialogFilter:Boolean= false,
    val ItemBPList: MutableList<String> = mutableListOf("Sin Datos"),
    val BPSapList: List<BusinessPartner?> = listOf(),
    val BPDeviceList: List<BusinessPartner?> = listOf(),
    val update: Boolean = false,
    val FilterByName: String = ""
)
