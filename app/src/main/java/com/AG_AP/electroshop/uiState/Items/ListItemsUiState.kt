package com.AG_AP.electroshop.uiState.Items

import com.AG_AP.electroshop.realm.models.Item

data class ListItemsUiState(
    val ListItems: List<Item?> = listOf(),
    val message: Boolean = false,
    val progress: Boolean = false,
    val text: String = ""
)