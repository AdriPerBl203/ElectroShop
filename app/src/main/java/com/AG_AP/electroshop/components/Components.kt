package com.AG_AP.electroshop.components

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TopBarButton(text: String, calback:()->Unit,icon: ImageVector) {
    TextButton(
        onClick = { calback() }
    ) {
        Text(text)
        Icon(imageVector = icon, contentDescription = "emailIcon")
    }
}