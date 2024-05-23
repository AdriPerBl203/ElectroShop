package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class DocumentLineFireBase : RealmObject  {
    val ItemCode: String = ""
    val ItemDescription: String = ""
    val Quantity: Double = 0.0
    val DiscountPercent: Double = 0.0
    val LineNum: Int = 0
    val Price: Double = 0.0
}
