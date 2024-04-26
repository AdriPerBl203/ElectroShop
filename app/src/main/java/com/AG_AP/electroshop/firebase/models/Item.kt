package com.AG_AP.electroshop.firebase.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Item(
    val itemName: String,
    val itemType: ItemType,
    val mainSupplier: String,
    val itemPrice: List<Price>?
) {
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["Series"] = 73
        hashMap["ItemName"] = itemName
        hashMap["ItemType"] = itemType
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
        return hashMap
    }

    fun fromHashMap(map: HashMap<String, Any>): Item {
        val itemName = map["ItemName"] as String
        val itemType = map["ItemType"] as ItemType
        val mainSupplier = map["Mainsupplier"] as String
        val itemPrices = if (map.containsKey("ItemPrices")) {
            val priceList = map["ItemPrices"] as List<HashMap<String, Any>>
            priceList.map { precioMap ->
                Price(
                    precioMap["PriceList"] as Int,
                    precioMap["Price"] as Int,
                    precioMap["Currency"] as String
                )
            }

        } else {
            null
        }
        return Item(itemName, itemType, mainSupplier, itemPrices)
    }
}
