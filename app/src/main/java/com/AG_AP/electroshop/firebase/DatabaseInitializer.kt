package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network

internal object DatabaseInitializer {
/*
    @SuppressLint("StaticFieldLeak")
    open var database: FirebaseFirestore = getInstance()

    /*
    init {
        // Registra el listener de conectividad cuando se crea una instancia de la clase.
        registerConnectivityListener()
    }

     */

    private fun getInstance(): FirebaseFirestore {
        database = FirebaseFirestore.getInstance()

        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings { setupCacheSize() })
            setLocalCacheSettings(persistentCacheSettings {})
        }

        database.firestoreSettings = settings

        return database
    }

    private fun setupCacheSize() {
        val settings = firestoreSettings {
            setLocalCacheSettings(persistentCacheSettings {
                // Set size to 500 MB
                setSizeBytes(1024 * 1024 * 500)
            })
        }
        database.firestoreSettings = settings
    }
    /*

    private fun registerConnectivityListener() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.registerDefaultNetworkCallback(networkCallback)

    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            // Cuando se detecta que hay conectividad disponible, habilita la red.
            val settings = firestoreSettings {
                setLocalCacheSettings(memoryCacheSettings { setupCacheSize() })
                setLocalCacheSettings(persistentCacheSettings {})
            }
            database.firestoreSettings = settings
        }

        override fun onLost(network: Network) {
            // Cuando se pierde la conectividad, deshabilita la red y usa la caché local.
            val settings = firestoreSettings {
                setLocalCacheSettings(memoryCacheSettings { setupCacheSize() })
                setLocalCacheSettings(persistentCacheSettings {
                    setSizeBytes(1024 * 1024 * 500)
                })
            }
            database.firestoreSettings = settings
        }
    }

     */

 */

}