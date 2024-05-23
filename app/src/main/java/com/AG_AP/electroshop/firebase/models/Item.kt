package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class Item(
    var idFireBase: String? = null,
    var ItemCode:String,
    var itemName: String,
    var itemType: ItemType,
    var mainSupplier: String?,
    var itemPrice: List<Price>?,
    var manageSerialNumbers: String,
    var autoCreateSerialNumbersOnRelease: String,
    var SAP: Boolean
) : RealmObject {
    fun toHashMap(): HashMap<String, Any?> {
        val hashMap = HashMap<String, Any?>()
        idFireBase?.let { hashMap["idFireBase"] = it }
        hashMap["Series"] = 73
        hashMap["ItemName"] = itemName
        hashMap["ItemType"] = itemType
        when (itemType) {
            ItemType.Articulo -> hashMap["ItemType"] = "I"
            ItemType.Servicio -> hashMap["ItemType"] = "L"
            ItemType.ActivoFijo -> hashMap["ItemType"] = "F"
            ItemType.Viaje -> hashMap["ItemType"] = "T"
        }
        hashMap["ItemCode"] = ItemCode
        hashMap["Mainsupplier"] = mainSupplier
        if (!itemPrice.isNullOrEmpty()) {
            val itemPricesList = mutableListOf<HashMap<String, Any>>()
            for (precio in itemPrice!!) {
                val precioMap = HashMap<String, Any>()
                precioMap["PriceList"] = precio.priceList
                precioMap["Price"] = precio.price
                precioMap["Currency"] = precio.currency
                itemPricesList.add(precioMap)
            }
            hashMap["ItemPrices"] = itemPricesList
        }
        hashMap["ManageSerialNumbers"] = manageSerialNumbers
        hashMap["AutoCreateSerialNumbersOnRelease"] = autoCreateSerialNumbersOnRelease
        hashMap["SAP"] = SAP
        return hashMap
    }
}
