package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.ItemInterface
import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import com.AG_AP.electroshop.endpoints.models.item.postItems.PostItem
import com.AG_AP.electroshop.endpoints.models.specialPrices.SpecialPrices
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

    suspend fun getItemsExten(urlInt: String,id:Int): GetItems? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(ItemInterface::class.java)
        var request: GetItems? = null
        return try {
            apiService.getItemsExten(id)
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    suspend fun postItems(rulUse: String, data: PostItem):Boolean {
        RetrofitClient.baseUrl = rulUse
        val apiService = RetrofitClient.retrofit.create(ItemInterface::class.java)
        return try {
            apiService.postItems(data)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    suspend fun getSpecialPrices(urlInt: String): SpecialPrices? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(ItemInterface::class.java)
        var request: GetItems? = null
        return try {
            apiService.getSpecialPrices()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}