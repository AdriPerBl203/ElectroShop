package com.AG_AP.electroshop.endpoints.models.orders.orderFilterClientAndDocument

data class Value(
    val CardCode: String,
    val DocDueDate: String,
    val DocumentLines: List<DocumentLine>
)