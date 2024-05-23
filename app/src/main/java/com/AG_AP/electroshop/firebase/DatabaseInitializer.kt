package com.AG_AP.electroshop.firebase


import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.firebase.models.SEIConfig
import com.AG_AP.electroshop.firebase.models.SpecialPriceFireBase
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object DatabaseInitializer {

    val configuration = RealmConfiguration.create(
        schema = setOf(
            Activity::class,
            BusinessPartner::class,
            Item::class,
            Price::class,
            SEIConfig::class,
            SpecialPriceFireBase::class
        )
    )

    val realm = getInstance()

    private fun getInstance(): Realm {
        return Realm.open(configuration)
    }

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