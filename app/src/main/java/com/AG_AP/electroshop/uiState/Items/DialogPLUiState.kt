package com.AG_AP.electroshop.uiState.Items

import com.AG_AP.electroshop.firebase.models.Price

data class DialogPLUiState(
    val AvailablePriceList: List<Price?> = listOf(),

    val PriceList: Number? = 0,
    val ChoosenCurrency: String? = "",
    val PriceWritten: Double? = 0.0,
    val ItemPrice: Price? = null
)
