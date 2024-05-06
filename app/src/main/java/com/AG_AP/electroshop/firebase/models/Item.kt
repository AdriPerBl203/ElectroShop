package com.AG_AP.electroshop.firebase.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Item(
    val ItemCode:String,
    val itemName: String,
    val itemType: ItemType,
    val mainSupplier: String,
    val itemPrice: List<Price>?,
    val manageSerialNumbers: String,
    val autoCreateSerialNumbersOnRelease: String,
    val SAP: Boolean
) {
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["Series"] = 73
        hashMap["ItemName"] = itemName
        hashMap["ItemType"] = itemType
        hashMap["ItemCode"] = ItemCode
        hashMap["Mainsupplier"] = mainSupplier
        if (!itemPrice.isNullOrEmpty()) {
            val itemPricesList = mutableListOf<HashMap<String, Any>>()
            for (precio in itemPrice) {
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

    fun fromHashMap(map: HashMap<String, Any>): Item {
        val itemName = map["ItemName"] as String
        val ItemCode = map["ItemCode"] as String
        val itemType = map["ItemType"] as ItemType
        val mainSupplier = map["Mainsupplier"] as String
        val itemPrices = if (map.containsKey("ItemPrices")) {
            val priceList = map["ItemPrices"] as List<HashMap<String, Any>>
            priceList.map { precioMap ->
                Price(
                    precioMap["PriceList"] as Int,
                    precioMap["Price"] as Int,
                    precioMap["Currency"] as String,
                    precioMap["SAP"].toString().toBoolean()
                )
            }
        } else {
            null
        }
        val manageSerialNumbers = map["ManageSerialNumber"] as String
        val autoCreateSerialNumbersOnRelease = map["AutoCreateSerialNumbersOnRelease"] as String
        val SAP = map["SAP"] as Boolean
        return Item(ItemCode,itemName, itemType, mainSupplier, itemPrices, manageSerialNumbers, autoCreateSerialNumbersOnRelease, SAP)
    }
}
