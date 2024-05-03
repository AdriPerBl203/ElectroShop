package com.AG_AP.electroshop.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CircularIndicator(width: Dp){
    CircularProgressIndicator(
        modifier = Modifier.width(width).padding(start = 8.dp, top = 5.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}