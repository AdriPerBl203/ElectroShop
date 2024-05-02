package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturn
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginInterface {

    @POST("b1s/v1/Login")
    suspend fun login(@Body data: Login): LoginReturn

    @POST("b1s/v1/Logout")
    suspend fun logout()
}