package com.AG_AP.electroshop.firebase.models

data class DocumentLineFireBase(
    val ItemCode: String,
    val Quantity: Double,
    val DiscountPercent: Double,
    val LineNum: Int,
    val Price: Double,
    val SAP: Boolean
){
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["ItemCode"] = this.ItemCode
        hashMap["Quantity"] = this.Quantity
        hashMap["DiscountPercent"] = this.DiscountPercent
        hashMap["LineNum"] = this.LineNum
        hashMap["Price"] = this.Price
        hashMap["SAP"] = this.SAP
        return hashMap
    }
}
