package com.AG_AP.electroshop.uiState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.ui.graphics.vector.ImageVector

data class SettingUiState(
    val login:String = "",
    val password:String = "",
    val dataBase:String = "",
    val urlCheck:String ="",
    val urlTipCheck:String ="",
    val urlExt:String = "",
    val urlInt:String = "",
    val ButtomEnable:Boolean = false,
    val text:String = "",
    val message:Boolean = false,
    val progress:Boolean = false,
    val init:Boolean = true,
    val syncProgress:Boolean = false,
    val checkUserUdo:Boolean = false,
    val checkBusinessPartner:Boolean = false,
    val checkActivity:Boolean = false,
    val checkItem:Boolean = false,
    val checkOrder:Boolean = false,
    val checkPurchaseOrder:Boolean = false,
    val checkPreciosEspeciales:Boolean = false,
    val checkPriceLists:Boolean = false,
    val textShow:Boolean =true,
    val btnSyncEnable:Boolean =true,
    val btnEnable:Boolean =true,
    val btnExitEnable:Boolean =true,
    val iconInt:ImageVector = Icons.Default.Sync,
    val iconExt:ImageVector = Icons.Default.Sync
)
/*
*
* val login:String = "manager",
    val password:String = "Usuario1234+",
    val dataBase:String = "PEPITO_ES",
    val urlExt:String = "https://10.129.22.179:50000/",
    val urlInt:String = "https://10.129.22.179:50000/",
* */