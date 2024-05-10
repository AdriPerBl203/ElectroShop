package com.AG_AP.electroshop.endpoints.models.priceList

import com.google.gson.annotations.SerializedName

data class PriceList(

    @SerializedName("odata.metadata")
    val odataMetadata: String,
    val value: List<Value>
)