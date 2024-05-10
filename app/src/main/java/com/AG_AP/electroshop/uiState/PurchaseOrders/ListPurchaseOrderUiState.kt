package com.AG_AP.electroshop.uiState.PurchaseOrders

import com.AG_AP.electroshop.firebase.models.OrderFireBase

data class ListPurchaseOrderUiState(
    val ListPurchaseOrder: List<OrderFireBase?> = listOf(),
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = ""
)
