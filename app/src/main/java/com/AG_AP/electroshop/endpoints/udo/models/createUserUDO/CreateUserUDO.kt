package com.AG_AP.electroshop.endpoints.udo.models.createUserUDO

data class CreateUserUDO(
    val Code: String,
    val U_SEIempleado: Int,
    val U_SEIpedidoCO: String,
    val U_SEIpedidoCl: String,
    val U_SEIactividad: String,
    val U_SEIarticulo: String,
    val U_SEIname: String,
    val U_SEIpassword: String
)