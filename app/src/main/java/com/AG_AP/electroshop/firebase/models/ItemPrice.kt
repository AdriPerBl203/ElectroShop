package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class ItemPrice : RealmObject {
    var priceList: Int = 0
    var price: Double = 0.0
    var quantity: Double = 0.0
    var discount: Double = 0.0
    var currency: String = ""
    var SAP: Boolean = false
}

