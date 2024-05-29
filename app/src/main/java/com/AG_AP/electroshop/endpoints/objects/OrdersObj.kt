package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.ItemInterface
import com.AG_AP.electroshop.endpoints.interfaces.OrdersInterface
import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import com.AG_AP.electroshop.endpoints.models.orders.Orders
import com.AG_AP.electroshop.endpoints.models.orders.orderFilterClientAndDocument.OrdersFilterClientAndDocumentLines
import com.AG_AP.electroshop.endpoints.models.orders.post.PostOrder
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient

object OrdersObj {

    suspend fun getOrders(urlInt: String): Orders? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(OrdersInterface::class.java)
        return try {
            apiService.getOrders()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    suspend fun getClientAndDocumentLinesFilter(urlInt: String): OrdersFilterClientAndDocumentLines?{
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(OrdersInterface::class.java)
        return try {
            apiService.getClientAndDocumentLinesFilter()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    suspend fun postOrders(urlInt: String,data: PostOrder): Boolean {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(OrdersInterface::class.java)
        return try {
            apiService.postOrders(data)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    suspend fun getOrdersExtenExten(urlInt: String, num: Int): Orders? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(OrdersInterface::class.java)
        return try {
            apiService.getOrdersExten(num)
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}