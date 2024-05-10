package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.priceList.PriceList
import retrofit2.http.GET

interface PriceListInterface {

    @GET("b1s/v1/PriceLists")
    suspend fun getPriceLists(): PriceList
}