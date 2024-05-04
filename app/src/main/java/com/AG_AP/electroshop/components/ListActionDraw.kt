package com.AG_AP.electroshop.components

import androidx.compose.ui.graphics.vector.ImageVector

data class ListActionDraw(
    val text: String,
    val icon: ImageVector,
    val textDialog: String,
    val action: () -> Unit
)
