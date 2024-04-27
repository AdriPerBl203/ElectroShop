package com.AG_AP.electroshop.uiState

data class BusinessPartnerUiState(
    val CardCode:String ="",
    val CardType:String ="",
    val CardName:String ="",
    val Cellular:String ="",
    val EmailAddress:String ="",
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = ""
)
