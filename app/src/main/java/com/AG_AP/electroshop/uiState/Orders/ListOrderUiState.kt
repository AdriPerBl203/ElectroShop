package com.AG_AP.electroshop.uiState.Orders

import com.AG_AP.electroshop.firebase.models.OrderFireBase

data class ListOrderUiState(
    val ListOrder: List<OrderFireBase?> = listOf(),
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = ""
)
