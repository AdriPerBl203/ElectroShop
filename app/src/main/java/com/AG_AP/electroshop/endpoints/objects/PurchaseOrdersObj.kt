package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.PurchaseOrdersInteface
import com.AG_AP.electroshop.endpoints.models.purchaseOrders.PurchaseOrders
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient

object PurchaseOrdersObj {

    suspend fun getPurchaseOrders(urlInt: String): PurchaseOrders? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(PurchaseOrdersInteface::class.java)
        return try {
            apiService.getPurchaseOrders()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}