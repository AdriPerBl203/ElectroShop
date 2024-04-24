package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.ActivityInterface
import com.AG_AP.electroshop.endpoints.models.activity.Activity
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient

object ActivityObj {
    suspend fun getActivities(urlInt: String): Activity? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(ActivityInterface::class.java)
        return try {
            apiService.getActivity()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}