package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class SpecialPriceFireBase: RealmObject {
    var ItemCode: String = ""
    var CardCode: String = ""
    var Price: Double = 0.0
    var Currency: String = ""
    var DiscountPercent: Double = 0.0
    var PriceListNum: Int = 0
}
