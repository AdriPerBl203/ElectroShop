package com.AG_AP.electroshop.endpoints.objects

import android.util.Log
import com.AG_AP.electroshop.endpoints.interfaces.LoginInterface
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturn
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient
import com.AG_AP.electroshop.funtions.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginObj {

    suspend fun loginAcessTwoversion(data: Login, urlInt: String): Boolean {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(LoginInterface::class.java)
        var request:LoginReturn? = null
        try {
            request = apiService.login(data)
            Config.rulUse = urlInt
            Config.cookie = request.SessionId
            return true
        } catch (e: Exception) {
            return false
        }
    }
}