package com.AG_AP.electroshop.uiState.Items

import com.AG_AP.electroshop.firebase.models.ItemPrice

data class DialogPLUiState(
    val AvailablePriceList: List<ItemPrice?> = listOf(),

    val PriceList: Number? = 0,
    val ChoosenCurrency: String? = "",
    val PriceWritten: Double? = 0.0,
    val ItemPrice: ItemPrice? = null
)
