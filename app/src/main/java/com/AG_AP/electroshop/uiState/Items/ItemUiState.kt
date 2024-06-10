package com.AG_AP.electroshop.uiState.Items

import com.AG_AP.electroshop.realm.models.Item
import com.AG_AP.electroshop.realm.models.ItemPrice
import com.AG_AP.electroshop.realm.models.PriceListRealm

data class ItemUiState(
    val ItemCode: String = "",
    val itemName: String = "",
    val itemType: String = "I",
    val mainSupplier: String = "",
    val itemPrice: MutableList<ItemPrice>? = mutableListOf(),
    val itemsList: MutableList<Item>? = mutableListOf(),
    val PriceListObject: MutableList<PriceListRealm>? = mutableListOf(),
    val manageSerialNumbers: Boolean = false,
    val autoCreateSerialNumbersOnRelease: Boolean = false,
    val message: Boolean = false,
    val progress: Boolean = false,
    val text: String = "",
    val showBusinessPartnerDialog: Boolean = false,
    val showPriceListDialog: Boolean = false,
    val trash: Int = 0
)
