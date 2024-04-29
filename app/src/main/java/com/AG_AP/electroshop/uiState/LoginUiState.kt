package com.AG_AP.electroshop.uiState

data class LoginUiState(
    val username:String = "",
    val password:String = "",
    val message:Boolean = false,
    val text:String = "",
    val progress:Boolean = false,
    val paso:Boolean= false
)
