package com.AG_AP.electroshop.uiState

import com.AG_AP.electroshop.firebase.models.Activity

data class ListActivityUiState(
    val DateInit:String ="",
    val DateEnd:String ="",
    val Client:String ="",
    val Document:String ="Sin documento",
    val ListActivity: List<Activity?> = listOf(),
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = ""
)
