package com.AG_AP.electroshop.uiState

import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price

data class ItemUiState(
    val ItemCode:String = "",
    val itemName: String = "",
    val itemType: ItemType = ItemType.I,
    val mainSupplier: String = "",
    val itemPrice: List<Price>? = mutableListOf(),
    val manageSerialNumbers: String = "",
    val autoCreateSerialNumbersOnRelease: String = ""
)