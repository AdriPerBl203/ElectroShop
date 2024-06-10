package com.AG_AP.electroshop.uiState.Orders

import com.AG_AP.electroshop.realm.models.OrderRealm

data class ListOrderUiState(
    val ListOrder: List<OrderRealm?> = listOf(),
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = ""
)
