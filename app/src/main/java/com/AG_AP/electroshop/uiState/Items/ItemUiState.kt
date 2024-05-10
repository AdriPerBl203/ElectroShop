package com.AG_AP.electroshop.uiState.Items

import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price

data class ItemUiState(
    val ItemCode: String = "",
    val itemName: String = "",
    val itemType: ItemType = ItemType.Articulo,
    val mainSupplier: String = "",
    val itemPrice: MutableList<Price>? = mutableListOf(),
    val manageSerialNumbers: Boolean = false,
    val autoCreateSerialNumbersOnRelease: Boolean = false,
    val message: Boolean = false,
    val progress: Boolean = false,
    val text: String = "",
    val showBusinessPartnerDialog: Boolean = false,
    val showPriceListDialog: Boolean = false,
    val trash: Int = 0
)
