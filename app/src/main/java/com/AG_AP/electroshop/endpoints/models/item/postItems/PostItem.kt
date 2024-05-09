package com.AG_AP.electroshop.endpoints.models.item.postItems

data class PostItem(
    val Series: Int,
    val AutoCreateSerialNumbersOnRelease: String, // tNO por defecto
    val ItemName: String,
    val ItemPrices: List<ItemPrice>,
    val ItemType: String,
    val ManageSerialNumbers: String // tNO por defecto
    //val MainSupplier: String, // Proveedor principal //TODO mirar mas adelante
)