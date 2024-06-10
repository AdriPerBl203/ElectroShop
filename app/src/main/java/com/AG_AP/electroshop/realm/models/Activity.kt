package com.AG_AP.electroshop.realm.models

import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Activity : RealmObject {

    var idRealm: ObjectId = ObjectId()
    var idFireBase: String? = null
    var nota: String = ""
    var ActivityDate: String = ""
    var ActivityTime: String = ""
    var CardCode: String = ""
    var EndTime: String = ""
    var Action: String = ""
    var Tel: String = ""
    var ClgCode: String = ""
    var Priority: String = ""
    var U_SEIPEDIDOCOMPRAS: Int? = null
    var U_SEIPEDIDOCLIENTE: Int? = null
    var SAP: Boolean = false

}
