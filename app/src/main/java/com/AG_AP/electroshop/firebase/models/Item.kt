package com.AG_AP.electroshop.firebase.models

data class Item(
    val itemName: String,
    val itemType: ItemType,
    val mainSupplier: String,
    val itemPriceList: List<Price>
)
