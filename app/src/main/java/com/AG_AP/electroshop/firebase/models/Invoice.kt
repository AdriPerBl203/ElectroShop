package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class Invoice : RealmObject {
    var DocEntry: Long = -1
    var base64String: String = ""

}