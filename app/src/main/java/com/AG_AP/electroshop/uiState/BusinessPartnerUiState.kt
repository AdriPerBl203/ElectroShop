package com.AG_AP.electroshop.uiState

data class BusinessPartnerUiState(
    val LineNum: Int = 0,
    val CardCode:String ="",
    val CardType:String ="Cliente",
    val CardName:String ="",
    val Cellular:String ="",
    val EmailAddress:String ="",
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = ""
)
