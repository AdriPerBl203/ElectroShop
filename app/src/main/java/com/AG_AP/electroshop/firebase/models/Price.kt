package com.AG_AP.electroshop.firebase.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Price(
    val priceList: Int,
    val price: Number,
    val currency: String
) {
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["PriceList"] = priceList
        hashMap["Price"] = price
        hashMap["Currency"] = currency
        return hashMap
    }
}

