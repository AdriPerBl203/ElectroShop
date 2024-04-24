package com.AG_AP.electroshop.endpoints.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class CookiesInterceptor : Interceptor {
    private var routeId: String? = null
    private var b1Session: String? = null
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
        val modifiedRequest = modifiedRequestBuilder.build()
        val response = chain.proceed(modifiedRequest)
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
        }
        return response
    }
}