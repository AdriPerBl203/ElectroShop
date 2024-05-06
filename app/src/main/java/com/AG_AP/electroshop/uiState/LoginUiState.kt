package com.AG_AP.electroshop.uiState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.vector.ImageVector

data class LoginUiState(
    val username:String = "manager",
    val password:String = "usuario",
    val message:Boolean = false,
    val text:String = "",
    val progress:Boolean = false,
    val iconPass: ImageVector = Icons.Filled.VisibilityOff,
    val seePass: Boolean = false,
    val circularProgress:Boolean = false
)
