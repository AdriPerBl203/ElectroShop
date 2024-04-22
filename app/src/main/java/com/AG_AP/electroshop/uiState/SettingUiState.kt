package com.AG_AP.electroshop.uiState

data class SettingUiState(
    val login:String = "manager",
    val password:String = "Usuario1234*",
    val dataBase:String = "PEPITO_ES",
    val urlExt:String = "https://10.129.22.179:50000/",
    val urlInt:String = "https://10.129.22.179:50000/",
    val text:String = "",
    val message:Boolean = false,
    val progress:Boolean = false
)
