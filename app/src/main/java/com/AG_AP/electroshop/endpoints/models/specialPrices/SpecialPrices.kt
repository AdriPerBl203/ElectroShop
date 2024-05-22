package com.AG_AP.electroshop.endpoints.models.specialPrices

import com.google.gson.annotations.SerializedName

data class SpecialPrices(
    @SerializedName("odata.metadata")
    val odataMetadata: String,
    val value: List<Value>
)