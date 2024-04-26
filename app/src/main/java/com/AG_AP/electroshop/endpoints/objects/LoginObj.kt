package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.LoginInterface
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturn
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient
import com.AG_AP.electroshop.functions.Config

object LoginObj {

    suspend fun loginAcessTwoversion(data: Login, urlInt: String): Boolean {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(LoginInterface::class.java)
        var request:LoginReturn? = null
        return try {
            request = apiService.login(data)
            Config.rulUse = urlInt
            true
        } catch (e: Exception) {
            false
        }
    }
}