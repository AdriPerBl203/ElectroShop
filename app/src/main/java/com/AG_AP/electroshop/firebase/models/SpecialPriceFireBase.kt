package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

data class SpecialPriceFireBase(
    val ItemCode: String,
    val CardCode: String,
    val Price: Double,
    val Currency: String,
    val DiscountPercent: Double,
    val PriceListNum: Int
) : RealmObject {
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["ItemCode"] = ItemCode
        hashMap["CardCode"] = CardCode
        hashMap["Price"] = Price
        hashMap["Currency"] = Currency
        hashMap["DiscountPercent"] = DiscountPercent
        hashMap["PriceListNum"] = PriceListNum
        return hashMap
    }
}
