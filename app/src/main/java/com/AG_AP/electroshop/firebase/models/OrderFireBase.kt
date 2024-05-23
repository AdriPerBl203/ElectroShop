package com.AG_AP.electroshop.firebase.models

import com.AG_AP.electroshop.endpoints.models.orders.DocumentLine
import io.realm.kotlin.types.RealmObject

class OrderFireBase(
    var idFireBase: String? = null,
    var DocNum: Int,
    var CardCode: String,
    var CardName: String,
    var DocDate: String,
    var DocDueDate: String,
    var TaxDate: String,
    var DiscountPercent: Double,
    var DocumentLines: List<DocumentLineFireBase>,
    //TODO slpcode
    var SAP: Boolean,
    var SalesPersonCode:Int,
) : RealmObject {
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
            documentLineMap["ItemDescription"] = documentLine.ItemDescription
            documentLineMap["DiscountPercent"] = documentLine.DiscountPercent
            documentLineMap["LineNum"] = documentLine.LineNum
            documentLineMap["Price"] = documentLine.Price
            documentLinesList.add(documentLineMap)
        }
        hashMap["DocumentLines"] = documentLinesList
        hashMap["SAP"] = this.SAP
        hashMap["SalesPersonCode"] = this.SalesPersonCode
        return hashMap
    }
}
