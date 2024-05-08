package com.AG_AP.electroshop.uiState

data class MenuUiState (
    val username: String = "",
    val articulo:String ="N",
    val actividad:String ="N",
    val pedidoCL:String ="N",
    val pedidoCO:String ="N",
    val dialog:Boolean =false,
    val InfoDialog:String = "",
    val checkProgresCircular:Boolean = true,
    val TextOrList:Boolean = true,
    val numRandom:Int = 4364
)