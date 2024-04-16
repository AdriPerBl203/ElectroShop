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

    /*
    val retrofit: Retrofit by lazy {

        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    // No hacer nada, simplemente confiar
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    // No hacer nada, simplemente confiar
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf() // No devolver nada para aceptar todos los certificados
                }
            }
        )

        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustAllCerts, SecureRandom())
        }

        val okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true } // Permitir todos los nombres de host
            .connectTimeout(200, TimeUnit.SECONDS) // Establecer tiempo de espera de conexión
            .readTimeout(200, TimeUnit.SECONDS) // Establecer tiempo de espera de lectura
            .writeTimeout(200, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

     */

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

            return httpClientBuilder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getUnsafeOkHttpClient()) // Usa el cliente personalizado aquí
        .build()

    val apiService = retrofit.create(LoginInterface::class.java)
}