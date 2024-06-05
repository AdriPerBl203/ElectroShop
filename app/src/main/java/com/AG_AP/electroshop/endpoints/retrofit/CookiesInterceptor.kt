package com.AG_AP.electroshop.endpoints.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import android.util.Log

class CookiesInterceptor : Interceptor {
    private var routeId: String? = null
    private var b1Session: String? = null
    private var SESSION: String? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // Agregar las cookies a la solicitud si están presentes
        val modifiedRequestBuilder = request.newBuilder()
        if (b1Session != null) {
            modifiedRequestBuilder.addHeader("Cookie", "B1SESSION=$b1Session")
        }
        if (routeId != null) {
            modifiedRequestBuilder.addHeader("Cookie", "ROUTEID=$routeId")
        }
        if (SESSION != null) {
            modifiedRequestBuilder.addHeader("Cookie", "SESSION=$SESSION")
        }
        val modifiedRequest = modifiedRequestBuilder.build()
        val response = chain.proceed(modifiedRequest)
        if (!response.isSuccessful) {
            val responseBody = response.body
            val errorBody = responseBody?.string()
            Log.e("ERROR400RONIN", "Error body: $errorBody")
        }
        // Obtener las cookies de la respuesta
        val cookies = response.headers("Set-Cookie")
        // Iterar sobre las cookies para buscar las cookies "ROUTEID" y "B1SESSION"
        for (cookie in cookies) {
            if (cookie.startsWith("ROUTEID=")) {
                // Extraer el valor de la cookie "ROUTEID"
                routeId = cookie.split(";")[0].substringAfter("=")
            }
            if (cookie.startsWith("B1SESSION=")) {
                // Extraer el valor de la cookie "B1SESSION"
                b1Session = cookie.split(";")[0].substringAfter("=")
            }
            if (cookie.startsWith("SESSION=")) {
                // Extraer el valor de la cookie "SESSION"
                SESSION = cookie.split(";")[0].substringAfter("=")
            }
        }
        return response
    }
}