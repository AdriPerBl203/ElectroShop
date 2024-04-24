package com.AG_AP.electroshop.endpoints.models.item.getItems

import com.google.gson.annotations.SerializedName

data class Value(
    val ItemCode: String,
    val ItemName: String,
    val Mainsupplier: String,
    val ItemPrices: List<ItemPrice>,
    @SerializedName("odata.etag")
    val odataEtag: String
)