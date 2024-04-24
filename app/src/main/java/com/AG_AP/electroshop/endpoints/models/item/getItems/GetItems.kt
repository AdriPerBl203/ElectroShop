package com.AG_AP.electroshop.endpoints.models.item.getItems

import com.google.gson.annotations.SerializedName

data class GetItems(
    @SerializedName("odata.metadata")
    val odataMetadata: String,
    @SerializedName("odata.nextLink")
    val odataNextLink: String,
    val value: List<Value>
)