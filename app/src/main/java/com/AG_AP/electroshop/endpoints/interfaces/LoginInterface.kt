package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturn
import com.AG_AP.electroshop.endpoints.models.login.LoginReturnGateway
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginInterface {

    @POST("b1s/v1/Login")
    suspend fun login(@Body data: Login): LoginReturn

    @POST("login")
    suspend fun loginGateway(@Body data: Login): LoginReturnGateway

    //TODO: Add logout
    @POST("logout")
    suspend fun logoutGateway()

    @POST("b1s/v1/Logout")
    suspend fun logout()
}