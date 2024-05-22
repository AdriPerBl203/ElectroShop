package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

data class Price(
    val priceList: Int,
    val price: Number,
    val currency: String,
    val SAP: Boolean
) : RealmObject {
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["PriceList"] = priceList
        hashMap["Price"] = price
        hashMap["Currency"] = currency
        hashMap["SAP"] = SAP
        return hashMap
    }
}

