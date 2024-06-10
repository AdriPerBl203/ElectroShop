package com.AG_AP.electroshop.realm


import com.AG_AP.electroshop.realm.models.Activity
import com.AG_AP.electroshop.realm.models.BusinessPartner
import com.AG_AP.electroshop.realm.models.DocumentLineRealm
import com.AG_AP.electroshop.realm.models.InvoiceData
import com.AG_AP.electroshop.realm.models.Item
import com.AG_AP.electroshop.realm.models.OrderRealm
import com.AG_AP.electroshop.realm.models.ItemPrice
import com.AG_AP.electroshop.realm.models.PriceListRealm
import com.AG_AP.electroshop.realm.models.SEIConfig
import com.AG_AP.electroshop.realm.models.SpecialPriceRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object DatabaseInitializer {

    val schemaVersion = RealmConfiguration.Builder(
        schema = setOf(
            Activity::class,
            BusinessPartner::class,
            Item::class,
            ItemPrice::class,
            SEIConfig::class,
            SpecialPriceRealm::class,
            OrderRealm::class,
            DocumentLineRealm::class,
            PriceListRealm::class,
            InvoiceData::class
        )
    )
        .directory("data/data/com.AG_AP.electroshop/databases")
        .name("electroshop.realm")
        .schemaVersion(3)
        .deleteRealmIfMigrationNeeded()
        .build()

    val realm = getInstance()

    private fun getInstance(): Realm {
        return Realm.open(schemaVersion)
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