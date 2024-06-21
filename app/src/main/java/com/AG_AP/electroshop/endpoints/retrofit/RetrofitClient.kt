package com.AG_AP.electroshop.endpoints.retrofit

import com.AG_AP.electroshop.endpoints.interfaces.LoginInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object RetrofitClient {

    var baseUrl: String = "https://10.129.22.179:50000/" // URL base predeterminada
    var baseUrlGetaway: String = "https://10.129.22.179:50000/" // URL base predeterminada


    fun getUnsafeOkHttpClient(): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory


            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            httpClientBuilder.hostnameVerifier { _, _ -> true }
                .connectTimeout(120, TimeUnit.SECONDS) // Establecer tiempo de espera de conexión
                .readTimeout(120, TimeUnit.SECONDS) // Establecer tiempo de espera de lectura
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(CookiesInterceptor())
            //
            //

            return httpClientBuilder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    var retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getUnsafeOkHttpClient()) // Usa el cliente personalizado aquí
        .build()

    var retrofitGetaway = Retrofit.Builder()
        .baseUrl(baseUrlGetaway)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getUnsafeOkHttpClient()) // Usa el cliente personalizado aquí
        .build()

    fun setURL(url: String) {
        baseUrl = url
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getUnsafeOkHttpClient()) // Usa el cliente personalizado aquí
            .build()
    }

    fun setURLGetaway(url: String) {
        //var spliData :List<String> = url.split(":")
        //val urlCustonPort = spliData.get(0) + ":" + spliData.get(1) + ":60020/"

        baseUrlGetaway = url
        retrofitGetaway = Retrofit.Builder()
            .baseUrl(baseUrlGetaway)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getUnsafeOkHttpClient()) // Usa el cliente personalizado aquí
            .build()
    }
}