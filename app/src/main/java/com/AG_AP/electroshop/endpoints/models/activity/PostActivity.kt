package com.AG_AP.electroshop.endpoints.models.activity

data class PostActivity(
    val Activity: String,
    val ActivityDate: String,
    val ActivityTime: String,
    val CardCode: String,
    val EndTime: String,
    val Notes: String,
    val Priority: String,
    val U_SEIPEDIDOCLIENTE: Int,
    val U_SEIPEDIDOCOMPRAS: Int
)