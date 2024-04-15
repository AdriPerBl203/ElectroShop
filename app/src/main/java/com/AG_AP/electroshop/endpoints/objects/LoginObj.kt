package com.AG_AP.electroshop.endpoints.objects

import android.util.Log
import com.AG_AP.electroshop.endpoints.interfaces.LoginInterface
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturn
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient

object LoginObj {
    suspend fun loginAcces(data: Login, urlInt: String) {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(LoginInterface::class.java)

        try {
            val data = Login("PEPITO_ES","Usuario1234*","manager")
            val response: LoginReturn = apiService.login(data)
            Log.d("HTTP_REQUEST", "Response Code: ${response.SessionId}")
            println("a")
        } catch (e: Exception) {
            Log.e("HTTP_REQUESTAaron", "Error: ${e.message}", e)
            println("b")
        }
    }
}