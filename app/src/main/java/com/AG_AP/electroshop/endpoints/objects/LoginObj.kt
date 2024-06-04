package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.LoginInterface
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturn
import com.AG_AP.electroshop.endpoints.models.login.LoginReturnGateway
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient
import com.AG_AP.electroshop.functions.Config

object LoginObj {

    suspend fun loginAcessTwoversion(data: Login, url: String): Boolean {
        RetrofitClient.setURL(url)
        val apiService = RetrofitClient.retrofit.create(LoginInterface::class.java)
        var request:LoginReturn? = null
        return try {
            request = apiService.login(data)
            Config.rulUse = url
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun logout(urlInt: String): Boolean {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(LoginInterface::class.java)
        return try {
            apiService.logout()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun loginAcessGateway(data: Login, url: String): Boolean {
        RetrofitClient.setURLGetaway(url)
        val apiService = RetrofitClient.retrofitGetaway.create(LoginInterface::class.java)
        var request:LoginReturnGateway? = null
        return try {
            request = apiService.loginGateway(data)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun logoutGateway(urlInt: String): Boolean {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(LoginInterface::class.java)
        return try {
            apiService.logoutGateway()
            true
        } catch (e: Exception) {
            false
        }
    }
}