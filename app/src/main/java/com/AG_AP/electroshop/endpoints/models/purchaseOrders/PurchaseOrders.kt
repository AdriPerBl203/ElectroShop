package com.AG_AP.electroshop.endpoints.models.purchaseOrders

import com.google.gson.annotations.SerializedName

data class PurchaseOrders(
    @SerializedName("odata.metadata")
    val odataMetadata: String,
    @SerializedName("odata.nextLink")
    val odataNextLink: String,
    val value: List<Value>
)