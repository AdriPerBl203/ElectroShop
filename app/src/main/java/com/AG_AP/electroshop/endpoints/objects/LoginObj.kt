package com.AG_AP.electroshop.endpoints.objects

import android.util.Log
import com.AG_AP.electroshop.endpoints.interfaces.LoginInterface
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturn
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginObj {
    fun loginAcces(data: Login, urlInt: String): LoginReturn? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(LoginInterface::class.java)

        val call = apiService.login(data)

        var returnResponse: LoginReturn? = null


        call.enqueue(object : Callback<LoginReturn> {
            override fun onResponse(call: Call<LoginReturn>, response: Response<LoginReturn>) {
                if (response.isSuccessful) {
                    val respuesta = response.body()
                    returnResponse = respuesta
                } else {
                    val errorBody = response.errorBody()?.string()
                    returnResponse = null

                }
            }

            override fun onFailure(call: Call<LoginReturn>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return returnResponse
    }
}