package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class Price : RealmObject {
    var priceList: Int = 0
    var price: Number = 0
    var currency: String = ""
    var SAP: Boolean = false
}

