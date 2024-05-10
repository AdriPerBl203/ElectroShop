package com.AG_AP.electroshop.uiState.BusinessPartners

data class BusinessPartnerUiState(
    val LineNum: Int = 0,
    val CardCode:String ="",
    val CardType:String ="Cliente",
    val CardName:String ="RDM",
    val Cellular:String ="654567876",
    val EmailAddress:String ="rdm@gmai.com",
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = ""
)
