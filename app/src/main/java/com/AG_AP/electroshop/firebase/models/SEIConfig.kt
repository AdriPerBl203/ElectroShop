package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

data class SEIConfig(
    val Code: Int,
    val U_Empleado: Int,
    val U_name: String,
    val U_password: String,
    val U_articulo: String,
    val U_actividad: String,
    val U_PedidoCI: String,
    val U_PedidoCO: String,
) : RealmObject {
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["Code"] = Code
        hashMap["U_Empleado"] = U_Empleado
        hashMap["U_name"] = U_name
        hashMap["U_password"] = U_password
        hashMap["U_articulo"] = U_articulo
        hashMap["U_actividad"] = U_actividad
        hashMap["U_PedidoCI"] = U_PedidoCI
        hashMap["U_PedidoCO"] = U_PedidoCO
        return hashMap
    }
    fun fromHashMap(map: HashMap<String, Any>): SEIConfig {
        return SEIConfig(
            Code = map["Code"] as Int,
            U_Empleado = map["U_Empleado"] as Int,
            U_name = map["U_name"] as String,
            U_password = map["U_password"] as String,
            U_articulo = map["U_articulo"] as String,
            U_actividad = map["U_actividad"] as String,
            U_PedidoCI = map["U_PedidoCI"] as String,
            U_PedidoCO = map["U_PedidoCO"] as String
        )
    }
}
