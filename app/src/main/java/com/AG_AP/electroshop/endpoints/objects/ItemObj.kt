package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.ItemInterface
import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient

object ItemObj {
    suspend fun getItems(urlInt: String): GetItems? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(ItemInterface::class.java)
        var request: GetItems? = null
        return try {
            apiService.getItems()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}