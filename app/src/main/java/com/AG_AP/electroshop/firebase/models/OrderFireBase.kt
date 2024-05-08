package com.AG_AP.electroshop.firebase.models

import com.AG_AP.electroshop.endpoints.models.orders.DocumentLine

data class OrderFireBase(
    val idFireBase: String? = null,
    val DocNum: Int,
    val CardCode: String,
    val CardName: String,
    val DocDate: String,
    val DocDueDate: String,
    val TaxDate: String,
    val DiscountPercent: Double,
    val DocumentLines: List<DocumentLineFireBase>,
    //TODO slpcode
    val SAP: Boolean
){
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        idFireBase?.let { hashMap["idFireBase"] = it }
        hashMap["DocNum"] = this.DocNum
        hashMap["CardCode"] = this.CardCode
        hashMap["CardName"] = this.CardName
        hashMap["DocDate"] = this.DocDate
        hashMap["DocDueDate"] = this.DocDueDate
        hashMap["TaxDate"] = this.TaxDate
        hashMap["DiscountPercent"] = this.DiscountPercent
        val documentLinesList = ArrayList<HashMap<String, Any>>()
        for (documentLine in this.DocumentLines) {
            val documentLineMap = HashMap<String, Any>()
            documentLineMap["ItemCode"] = documentLine.ItemCode
            documentLineMap["Quantity"] = documentLine.Quantity
            documentLineMap["DiscountPercent"] = documentLine.DiscountPercent
            documentLineMap["LineNum"] = documentLine.LineNum
            documentLineMap["Price"] = documentLine.Price
            documentLinesList.add(documentLineMap)
        }
        hashMap["DocumentLines"] = documentLinesList
        hashMap["SAP"] = this.SAP
        return hashMap
    }
}
