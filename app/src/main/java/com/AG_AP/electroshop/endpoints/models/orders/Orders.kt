package com.AG_AP.electroshop.endpoints.models.orders

import com.google.gson.annotations.SerializedName

data class Orders(
    @SerializedName("odata.metadata")
    val odataMetadata: String,
    val value: List<Value>,
    @SerializedName("odata.nextLink")
    val odataNextLink: String,
)