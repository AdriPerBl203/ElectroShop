package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class DocumentLineFireBase : RealmObject  {
    var ItemCode: String = ""
    var ItemDescription: String = ""
    var Quantity: Double = 0.0
    var DiscountPercent: Double = 0.0
    var LineNum: Int = 0
    var Price: Double = 0.0
}
