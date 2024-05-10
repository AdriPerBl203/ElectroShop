package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.PriceListInterface
import com.AG_AP.electroshop.endpoints.models.priceList.PriceList
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient

object PriceListObj {

    suspend fun getPriceLists(urlInt: String): PriceList? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(PriceListInterface::class.java)
        return try {
            apiService.getPriceLists()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}