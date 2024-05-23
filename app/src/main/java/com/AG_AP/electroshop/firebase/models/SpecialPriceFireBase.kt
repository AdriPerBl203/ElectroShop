package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class SpecialPriceFireBase: RealmObject {
    val ItemCode: String = ""
    val CardCode: String = ""
    val Price: Double = 0.0
    val Currency: String = ""
    val DiscountPercent: Double = 0.0
    val PriceListNum: Int = 0
}
